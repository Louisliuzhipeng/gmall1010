package com.chirping.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chirping.gmall.service.UmsMemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘志鹏
 * @date 2019/10/10
 */
@RestController
public class UserController {

    @Reference
    UmsMemberService umsMemberService;

    @RequestMapping("getMember")
    public Object getMember() {
        return umsMemberService.getListUmsMember();
    }
}
