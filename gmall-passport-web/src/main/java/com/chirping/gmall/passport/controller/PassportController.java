package com.chirping.gmall.passport.controller;

import com.chirping.gmall.annotations.LoginRequired;
import com.chirping.gmall.pojo.UmsMember;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 刘志鹏
 * @date 2019/10/31
 */
@Controller
public class PassportController {

    @RequestMapping("verify")
    @ResponseBody
    public String verify(String token) {

        return "success";
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(UmsMember umsMember) {

        return "token";
    }

    @RequestMapping("index")
    @LoginRequired(loginSuccess = true)
    public String index(String ReturnUrl, Model model) {
        model.addAttribute("ReturnUrl", ReturnUrl);
        return "index";
    }
}
