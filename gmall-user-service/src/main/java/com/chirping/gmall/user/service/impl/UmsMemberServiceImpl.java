package com.chirping.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.chirping.gmall.mapper.UmsMemberMapper;
import com.chirping.gmall.pojo.UmsMember;
import com.chirping.gmall.service.UmsMemberService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘志鹏
 * @date $date$
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Resource
    UmsMemberMapper umsMemberMapper;

    @Override
    public List<UmsMember> getListUmsMember() {
        return umsMemberMapper.selectAll();
    }
}
