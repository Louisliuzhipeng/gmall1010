package com.chirping.gmall.passport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.utils.StringUtils;
import com.chirping.gmall.authBean.QQloginData;
import com.chirping.gmall.authBean.WeiBoLoginData;
import com.chirping.gmall.pojo.UmsMember;
import com.chirping.gmall.service.UmsMemberService;
import com.chirping.gmall.util.JwtUtil;
import com.google.common.base.Charsets;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.events.EndDocument;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void login(@PathVariable String type, AuthCallback callback, HttpServletResponse response1, HttpServletRequest request) throws IOException, ParseException {
        AuthRequest authRequest = factory.get(type);
        AuthResponse response = authRequest.login(callback);
        System.out.println(JSONObject.toJSONString(response));
        String userName = null;
        String access_token = null;
        Integer source_type = null;
        Long source_uid = null;
        String location = null;
        String icon = null;
        int gender = 0;
        if (type.equals("qq")) {
            QQloginData data = JSON.parseObject(JSONObject.toJSONString(response.getData()), QQloginData.class);
            userName = data.getUsername();
            source_uid = data.getUuid();
            access_token = data.getToken().getAccesstoken();
            source_type = 3;
            location = data.getLocation();
            icon = data.getAvatar();
            gender = data.getGender();
        }
        if (type.equals("weibo")) {
            WeiBoLoginData data = JSON.parseObject(JSONObject.toJSONString(response.getData()), WeiBoLoginData.class);
            userName = data.getUsername();
            source_uid = data.getUuid();
            access_token = data.getToken().getAccesstoken();
            source_type = 2;
            location = data.getLocation();
            icon = data.getAvatar();
            gender = data.getGender();
        }
        UmsMember umsMember = new UmsMember();
        umsMember.setNickname(userName);
        umsMember.setSourceUid(source_uid);
        umsMember.setSourceType(source_type);
        umsMember.setAccessToken(access_token);
        umsMember.setCity(location);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyyMMdd HHmmSS");
        umsMember.setCreateTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
        umsMember.setIcon(icon);
        umsMember.setGender(gender);

        UmsMember umsCheck = new UmsMember();
        umsCheck.setSourceUid(umsMember.getSourceUid());
        UmsMember memberCheck = umsMemberService.checkOauthUser(umsCheck);
        if (memberCheck == null) {
            umsMemberService.addOauthUser(umsMember);
        } else {
            umsMember = memberCheck;
        }

        String token = "";
        String memberId = umsMember.getId();
        String nickname = umsMember.getNickname();
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
        response1.sendRedirect("http://localhost:8083/index?token=" + token);
    }

}