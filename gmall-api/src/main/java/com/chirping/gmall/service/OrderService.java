package com.chirping.gmall.service;

import com.chirping.gmall.pojo.OmsOrder;

/**
 * @author 刘志鹏
 * @date $date$
 */
public interface OrderService {
    String checkTradeCode(String memberId, String tradeCode);

    String genTradeCode(String memberId);

    OmsOrder saveOrder(OmsOrder omsOrder);

    OmsOrder getOmsOrderItemByIdOrderId(String omsOrderId, String memberId, String orderId);

    OmsOrder getOrderByOutTradeNo(String outTradeNo);

    void updateOrder(OmsOrder omsOrder);
}
