package com.chirping.gmall.order.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.chirping.gmall.mq.ActiveMQUtil;
import com.chirping.gmall.util.RedisUtil;
import com.chirping.gmall.mapper.OmsOrderItemMapper;
import com.chirping.gmall.mapper.OmsOrderMapper;
import com.chirping.gmall.pojo.OmsOrder;
import com.chirping.gmall.pojo.OmsOrderItem;
import com.chirping.gmall.service.CartService;
import com.chirping.gmall.service.OrderService;
import org.apache.activemq.command.ActiveMQMapMessage;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.jms.*;
import javax.jms.Queue;
import java.util.*;

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

    @Resource
    ActiveMQUtil activeMQUtil;

    @Reference
    CartService cartService;

    @Override
    public String checkTradeCode(String memberId, String tradeCode) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            String tradeKey = "user:" + memberId + ":tradeCode";
            //String tradeCodeFromCache = jedis.get(tradeKey);// 使用lua脚本在发现key的同时将key删除，防止并发订单攻击
            //对比防重删令牌
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Long eval = (Long) jedis.eval(script, Collections.singletonList(tradeKey), Collections.singletonList(tradeCode));
            if (eval != null && eval != 0) {
                jedis.del(tradeKey);
                return "success";
            } else {
                return "fail";
            }
        } finally {
            jedis.close();
        }
    }

    @Override
    public String genTradeCode(String memberId) {
        Jedis jedis = redisUtil.getJedis();
        String tradeKey = "user:" + memberId + ":tradeCode";
        String tradeCode = UUID.randomUUID().toString();
        jedis.setex(tradeKey, 60 * 15, tradeCode);
        jedis.close();
        return tradeCode;
    }

    @Override
    public OmsOrder saveOrder(OmsOrder omsOrder) {
        // 保存订单表
        omsOrderMapper.insertSelective(omsOrder);
        String orderId = omsOrder.getId();
        Jedis jedis = redisUtil.getJedis();
        // 保存订单详情
        List<OmsOrderItem> omsOrderItems = omsOrder.getOmsOrderItems();
        for (OmsOrderItem omsOrderItem : omsOrderItems) {
            omsOrderItem.setOrderId(orderId);
            omsOrderItemMapper.insertSelective(omsOrderItem);
            // 删除购物车数据
            cartService.delCart(omsOrder.getMemberId());
        }
        cartService.flushCartCache(omsOrder.getMemberId());
        Map<String, String> map = new HashMap<>();
        map.put("order" + omsOrder.getId(), JSON.toJSONString(omsOrder));
        String orderKey = "order:" + omsOrder.getId() + ":Order";
        jedis.hmset(orderKey, map);
        jedis.close();
        return omsOrder;
    }

    @Override
    public OmsOrder getOmsOrderItemByIdOrderId(String omsOrderId, String memberId, String orderId) {
        Jedis jedis = redisUtil.getJedis();
        String orderKey = "order:" + omsOrderId + ":Order";
        List<String> hmget = jedis.hvals(orderKey);
        OmsOrder omsOrder = null;
        for (String hval : hmget) {
            omsOrder = JSON.parseObject(hval, OmsOrder.class);
        }
        return omsOrder;
    }

    @Override
    public OmsOrder getOrderByOutTradeNo(String outTradeNo) {
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderSn(outTradeNo);
        OmsOrder omsOrder1 = omsOrderMapper.selectOne(omsOrder);
        return omsOrder1;
    }

    @Override
    public void updateOrder(OmsOrder omsOrder) {
        Example e = new Example(OmsOrder.class);
        e.createCriteria().andEqualTo("orderSn",omsOrder.getOrderSn());
        OmsOrder omsOrderUpdate = new OmsOrder();
        omsOrderUpdate.setStatus(1);
        // 发送一个订单已支付的队列，提供给库存消费
        Connection connection = null;
        Session session = null;
        try{
            connection = activeMQUtil.getConnectionFactory().createConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Queue payhment_success_queue = session.createQueue("ORDER_PAY_QUEUE");
            MessageProducer producer = session.createProducer(payhment_success_queue);
            //TextMessage textMessage=new ActiveMQTextMessage();//字符串文本
            MapMessage mapMessage = new ActiveMQMapMessage();// hash结构
            omsOrderMapper.updateByExampleSelective(omsOrderUpdate,e);
            producer.send(mapMessage);
            session.commit();
        }catch (Exception ex){
            // 消息回滚
            try {
                session.rollback();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }

}
