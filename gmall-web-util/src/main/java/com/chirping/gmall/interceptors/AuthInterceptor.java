package com.chirping.gmall.interceptors;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.StringUtils;
import com.chirping.gmall.annotations.LoginRequired;
import com.chirping.gmall.util.CookieUtil;
import com.chirping.gmall.util.HttpclientUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘志鹏
 * @date 2019/10/31
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截代码
        //判断请求的方法上有没有拦截注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);
        HttpServletMapping httpServletMapping = request.getHttpServletMapping();
         if (loginRequired == null) {
            return true;
        }
        String token = "";
        String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
        String newToken = request.getParameter("token");
        if (StringUtils.isNotBlank(oldToken)) {
            token = oldToken;
        }
        if (StringUtils.isNotBlank(newToken)) {
            token = newToken;
        }
        boolean loginSuccess = loginRequired.loginSuccess();
        //验证
        String success = "fail";
        Map<String, String> successMap = new HashMap<>();
        if (StringUtils.isNotBlank(token)) {
            String ip = request.getHeader("x-forwarded-for");
            if (!checkIP(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (!checkIP(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip)) {
                ip = request.getRemoteAddr();
                if (!checkIP(ip)) {
                    ip = request.getRemoteAddr();
                    if (!checkIP(ip)) {
                        ip = "127.0.0.1";
                    }
                }
            }
            String successJson = HttpclientUtil.doGet("http://localhost:8085/verify?token=" + token + "&currentIP=" + ip);
            successMap = JSON.parseObject(successJson, Map.class);
            success = successMap.get("status");
        }
        if (loginSuccess) {
            if (!success.equals("success")) {
                //重定向登录
                StringBuffer requestURL = request.getRequestURL();
                response.sendRedirect("http://localhost:8085/index?ReturnUrl=" + requestURL);
                return false;
            }
            //验证通过
            request.setAttribute("memberId", successMap.get("memberId"));
            request.setAttribute("nickname", successMap.get("nickname"));
            //验证通过，覆盖cookie中的token
            if (StringUtils.isNotBlank(token)) {
                CookieUtil.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
            }
        } else {
            if (success.equals("success")) {
                request.setAttribute("memberId", successMap.get("memberId"));
                request.setAttribute("nickname", successMap.get("nickname"));
                //验证通过,覆盖cookie中的token
                if (StringUtils.isNotBlank(token)) {
                    CookieUtil.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
                }
            }
        }
        return true;
    }

    private static boolean checkIP(String ip) {
        if (ip == null || ip.length() == 0 || ip.split(".").length != 4) {
            return false;
        }
        return true;
    }
}
