package com.chirping.gmall.service;

import com.chirping.gmall.pojo.OmsCartItem;

import java.util.List;

/**
 * @author 刘志鹏
 * @date $date$
 */
public interface CartService {
    OmsCartItem ifCartExistByUser(String memberId, String skuId);

    void addCart(OmsCartItem omsCartItem);

    void updateCart(OmsCartItem omsCartItemFromDb);

    void flushCartCache(String memberId);

    List<OmsCartItem> cartList(String userId);

    void checkCart(OmsCartItem omsCartItem);

    void delCart(String memberId);
}
