package com.chirping.gmall.passport.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chirping.gmall.authBean.QQloginData;
import com.chirping.gmall.authBean.WeiBoLoginData;
import com.chirping.gmall.pojo.UmsMember;
import com.chirping.gmall.service.UmsMemberService;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 15211
 */
@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RestAuthController {

    @Reference
    UmsMemberService umsMemberService;

    private final AuthRequestFactory factory;

    @GetMapping
    @ResponseBody
    public List<String> list() {
        return factory.oauthList();
    }

    @GetMapping("/login/{type}")
    @ResponseBody
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback/{type}")
    public void login(@PathVariable String type, AuthCallback callback, HttpServletResponse response1) throws IOException {
        AuthRequest authRequest = factory.get(type);
        AuthResponse response = authRequest.login(callback);
        String userName = null;
        String access_token = null;
        int source_type = 0;
        Long source_uid = null;
        if (type.equals("qq")) {
            QQloginData data = JSON.parseObject(JSONObject.toJSONString(response.getData()), QQloginData.class);
            userName = data.getUsername();
            source_uid = Long.parseLong(data.getUuid());
            access_token = data.getToken().getAccesstoken();
            source_type = 3;
        }
        if (type.equals("weibo")) {
            WeiBoLoginData data = JSON.parseObject(JSONObject.toJSONString(response.getData()), WeiBoLoginData.class);
            userName = data.getUsername();
            source_uid = Long.parseLong(data.getUuid());
            access_token = data.getToken().getAccesstoken();
            source_type = 2;
        }
        UmsMember umsMember = new UmsMember();
        umsMember.setNickname(userName);
        umsMember.setSourceUid(source_uid);
        umsMember.setSourceType(source_type);
        umsMember.setAccessToken(access_token);

        umsMemberService.addOauthUser(umsMember);

        response1.sendRedirect("http://localhost:8083/index?userName" + userName);
    }

}