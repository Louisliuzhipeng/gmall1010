package com.chirping.gmall.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.chirping.gmall.mapper.OmsOrderItemMapper;
import com.chirping.gmall.mapper.OmsOrderMapper;
import com.chirping.gmall.pojo.OmsOrder;
import com.chirping.gmall.pojo.OmsOrderItem;
import com.chirping.gmall.service.CartService;
import com.chirping.gmall.service.OrderService;
import com.chirping.gmall.util.RedisUtil;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author 刘志鹏
 * @date $date$
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    RedisUtil redisUtil;

    @Resource
    OmsOrderMapper omsOrderMapper;

    @Resource
    OmsOrderItemMapper omsOrderItemMapper;

    @Reference
    CartService cartService;

    @Override
    public String checkTradeCode(String memberId, String tradeCode) {
        Jedis jedis = null ;
        try {
            jedis = redisUtil.getJedis();
            String tradeKey = "user:" + memberId + ":tradeCode";
            // 使用lua脚本在发现key的同时将key删除，防止并发订单攻击
            String tradeCodeFromCache = jedis.get(tradeKey);
            //对比防重删令牌
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Long eval = (Long) jedis.eval(script, Collections.singletonList(tradeKey), Collections.singletonList(tradeCode));
            if (eval!=null&&eval!=0) {
                // jedis.del(tradeKey);
                return "success";
            } else {
                return "fail";
            }
        }finally {
            jedis.close();
        }
    }

    @Override
    public String genTradeCode(String memberId) {
        Jedis jedis = redisUtil.getJedis();
        String tradeKey = "user:"+memberId+":tradeCode";
        String tradeCode = UUID.randomUUID().toString();
        jedis.setex(tradeKey,60*15,tradeCode);
        jedis.close();
        return tradeCode;
    }

    @Override
    public void saveOrder(OmsOrder omsOrder) {
        // 保存订单表
        omsOrderMapper.insertSelective(omsOrder);
        String orderId = omsOrder.getId();
        // 保存订单详情
        List<OmsOrderItem> omsOrderItems = omsOrder.getOmsOrderItems();
        for (OmsOrderItem omsOrderItem : omsOrderItems) {
            omsOrderItem.setOrderId(orderId);
            omsOrderItemMapper.insertSelective(omsOrderItem);
            // 删除购物车数据
            // cartService.delCart();
        }
    }
}
