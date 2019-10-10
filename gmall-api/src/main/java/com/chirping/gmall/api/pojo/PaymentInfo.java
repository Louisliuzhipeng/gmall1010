package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
@TableName(value = "payment_info")
public class PaymentInfo implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 对外业务编号
     */
    @TableField(value = "order_sn")
    private String orderSn;

    /**
     * 订单编号
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 支付宝交易编号
     */
    @TableField(value = "alipay_trade_no")
    private String alipayTradeNo;

    /**
     * 支付金额
     */
    @TableField(value = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 交易内容
     */
    @TableField(value = "subject")
    private String subject;

    /**
     * 支付状态
     */
    @TableField(value = "payment_status")
    private String paymentStatus;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 创建时间
     */
    @TableField(value = "confirm_time")
    private Date confirmTime;

    /**
     * 回调信息
     */
    @TableField(value = "callback_content")
    private String callbackContent;

    @TableField(value = "callback_time")
    private Date callbackTime;

    private static final long serialVersionUID = 1L;
}