package com.chirping.gmall.service;

import com.chirping.gmall.pojo.OmsOrder;

/**
 * @author 刘志鹏
 * @date $date$
 */
public interface OrderService {
    String checkTradeCode(String memberId, String tradeCode);

    String genTradeCode(String memberId);

    void saveOrder(OmsOrder omsOrder);
}
