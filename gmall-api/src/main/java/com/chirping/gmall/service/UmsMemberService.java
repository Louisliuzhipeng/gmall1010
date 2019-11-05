package com.chirping.gmall.service;

import com.chirping.gmall.pojo.UmsMember;
import com.chirping.gmall.pojo.UmsMemberReceiveAddress;

import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/10
 */
public interface UmsMemberService {

    List<UmsMember> getListUmsMember();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);

    void addUserToken(String token, String memberId);

    UmsMember login(UmsMember umsMember);

    UmsMember addOauthUser(UmsMember umsMember);

    UmsMember checkOauthUser(UmsMember umsCheck);

    UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId);
}
