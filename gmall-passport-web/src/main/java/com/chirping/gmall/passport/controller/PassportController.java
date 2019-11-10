package com.chirping.gmall.passport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.StringUtils;
import com.chirping.gmall.pojo.UmsMember;
import com.chirping.gmall.service.UmsMemberService;
import com.chirping.gmall.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘志鹏
 * @date 2019/10/31
 */
@Controller
public class PassportController {

    @Reference
    UmsMemberService umsMemberService;

    @RequestMapping("verify")
    @ResponseBody
    public String verify(String token, String currentIP, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Map<String, Object> decode = JwtUtil.decode(token, "2019gmall1010", currentIP);
        if (decode != null) {
            map.put("status", "success");
            map.put("memberId", decode.get("memberId").toString());
            map.put("nickname", decode.get("nickname").toString());
        } else {
            map.put("status", "fail");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(UmsMember umsMember, HttpServletRequest request) {
        String token = "";
        UmsMember member = umsMemberService.login(umsMember);
        if (member != null) {
            //登录成功
            // 用jwt制作token
            String memberId = member.getId();
            String nickname = member.getNickname();
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("memberId", memberId);
            userMap.put("nickname", nickname);
            // 通过nginx转发的客户端ip
            String ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ip)) {
                // 从request中获取ip
                ip = request.getRemoteAddr();
                if (StringUtils.isBlank(ip)) {
                    ip = "127.0.0.1";
                }
            }
            // 按照设计的算法对参数进行加密后，生成token
            token = JwtUtil.encode("2019gmall1010", userMap, ip);
            // 将token存入redis一份
            umsMemberService.addUserToken(token, memberId);
        } else {
            //登录失败
            token = "fail";
        }
        return token;
    }

    @RequestMapping("index")
    public String index(String ReturnUrl, Model model) {
        model.addAttribute("ReturnUrl", ReturnUrl);
        return "index";
    }
}
