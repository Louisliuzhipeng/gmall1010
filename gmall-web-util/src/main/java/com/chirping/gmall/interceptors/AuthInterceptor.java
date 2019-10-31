package com.chirping.gmall.interceptors;

import com.alibaba.nacos.client.utils.StringUtils;
import com.chirping.gmall.annotations.LoginRequired;
import com.chirping.gmall.util.CookieUtil;
import com.chirping.gmall.util.HttpclientUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘志鹏
 * @date 2019/10/31
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截代码
        //判断请求的方法上有没有拦截注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);
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
        if (StringUtils.isNotBlank(token)) {
            success = HttpclientUtil.doGet("http://localhost:8085/verify?token=" + token);
        }
        if (loginSuccess) {
            if (!success.equals("success")) {
                //重定向登录
                StringBuffer requestURL = request.getRequestURL();
                response.sendRedirect("http://localhost:8085/index?ReturnUrl=" + requestURL);
                return false;
            }
            //验证通过
            request.setAttribute("memberId", "");
            request.setAttribute("nickname", "小明");
            //验证通过，覆盖cookie中的token
            if(StringUtils.isNotBlank(token)){
                CookieUtil.setCookie(request,response,"oldToken",token,60*60*2,true);
            }
        } else {
            if (success.equals("success")) {
                request.setAttribute("memberId", "");
                request.setAttribute("nickname", "小明");
                //验证通过,覆盖cookie中的token
                if (StringUtils.isNotBlank(token)) {
                    CookieUtil.setCookie(request, response, "oldToken", token, 60 * 60 * 2, true);
                }
            }
        }
        return true;
    }
}
