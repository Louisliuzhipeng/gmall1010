package com.chirping.gmall.payment.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.chirping.gmall.mq.ActiveMQUtil;
import com.chirping.gmall.mapper.PaymentInfoMapper;
import com.chirping.gmall.payBean.PaymentInfo;
import com.chirping.gmall.service.PaymentService;
import org.apache.activemq.command.ActiveMQMapMessage;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.jms.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    PaymentInfoMapper paymentInfoMapper;

    @Resource
    ActiveMQUtil activeMQUtil;

    @Override
    public void savePaymentInfo(PaymentInfo paymentInfo) {
        paymentInfoMapper.insertSelective(paymentInfo);
    }


    @Override
    public void updatePayment(PaymentInfo paymentInfo) {
        String orderSn = paymentInfo.getOrderSn();
        Example e = new Example(PaymentInfo.class);
        e.createCriteria().andEqualTo("orderSn",orderSn);
        Connection connection = null;
        Session session = null;
        try {
            connection = activeMQUtil.getConnectionFactory().createConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try{
            paymentInfoMapper.updateByExampleSelective(paymentInfo,e);
            // 支付成功后，引起的系统服务-》订单服务的更新-》库存服务-》物流服务
            // 调用mq发送支付成功的消息
            Queue payhment_success_queue = session.createQueue("PAYHMENT_SUCCESS_QUEUE");
            MessageProducer producer = session.createProducer(payhment_success_queue);
            //TextMessage textMessage=new ActiveMQTextMessage();//字符串文本
            MapMessage mapMessage = new ActiveMQMapMessage();// hash结构
            mapMessage.setString("out_trade_no",paymentInfo.getOrderSn());
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
