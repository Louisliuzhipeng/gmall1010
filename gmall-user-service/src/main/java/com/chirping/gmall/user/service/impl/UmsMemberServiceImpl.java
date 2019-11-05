package com.chirping.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.StringUtils;
import com.chirping.gmall.mapper.UmsMemberMapper;
import com.chirping.gmall.mapper.UmsMemberReceiveAddressMapper;
import com.chirping.gmall.pojo.UmsMember;
import com.chirping.gmall.pojo.UmsMemberReceiveAddress;
import com.chirping.gmall.service.UmsMemberService;
import com.chirping.gmall.util.RedisUtil;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘志鹏
 * @date $date$
 */
@Service
@Transactional
public class UmsMemberServiceImpl implements UmsMemberService {

    @Resource
    UmsMemberMapper umsMemberMapper;
    @Resource
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;
    @Resource
    RedisUtil redisUtil;

    @Override
    public List<UmsMember> getListUmsMember() {
        return umsMemberMapper.selectAll();
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        // 封装的参数对象
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);
        return umsMemberReceiveAddresses;
    }

    @Override
    public UmsMember login(UmsMember umsMember) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            if (jedis != null) {
                String umsMemaberKey = umsMember.getUsername() + "-" + umsMember.getPassword();
                String umsMemberStr = jedis.get("user:" + umsMemaberKey + ":info");
                if (StringUtils.isNotBlank(umsMemberStr)) {
                    // 密码正确
                    UmsMember umsMemberFromCache = JSON.parseObject(umsMemberStr, UmsMember.class);
                    return umsMemberFromCache;
                }
            }
            //开启数据库
            UmsMember umsMemberFromDb = loginFromDb(umsMember);
            if (umsMemberFromDb != null) {
                String umsMemaberKey = umsMember.getUsername() + "-" + umsMember.getPassword();
                jedis.setex("user:" + umsMemaberKey + ":info", 60 * 60 * 24, JSON.toJSONString(umsMemberFromDb));
            }
            return umsMemberFromDb;
        } finally {
            jedis.close();
        }
    }

    @Override
    public UmsMember addOauthUser(UmsMember umsMember) {
        umsMemberMapper.insertSelective(umsMember);
        return umsMember;
    }

    @Override
    public UmsMember checkOauthUser(UmsMember umsCheck) {
        return umsMemberMapper.selectOne(umsCheck);
    }

    @Override
    public UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setId(receiveAddressId);
        UmsMemberReceiveAddress umsMemberReceiveAddress1 = umsMemberReceiveAddressMapper.selectOne(umsMemberReceiveAddress);
        return umsMemberReceiveAddress1;
    }

    @Override
    public void addUserToken(String token, String memberId) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            jedis.setex("user:" + memberId + ":token", 60 * 60 * 2, token);
        } finally {
            jedis.close();
        }
    }

    private UmsMember loginFromDb(UmsMember umsMember) {
        List<UmsMember> umsMembers = umsMemberMapper.select(umsMember);
        if (umsMembers != null && umsMembers.size() > 0) {
            return umsMembers.get(0);
        }
        return null;
    }
}
