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
@TableName(value = "oms_order")
public class OmsOrder implements Serializable {
    /**
     * 订单id
     */
     @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "member_id")
    private Long memberId;

    @TableField(value = "coupon_id")
    private Long couponId;

    /**
     * 订单编号
     */
    @TableField(value = "order_sn")
    private String orderSn;

    /**
     * 提交时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 用户帐号
     */
    @TableField(value = "member_username")
    private String memberUsername;

    /**
     * 订单总金额
     */
    @TableField(value = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 应付金额（实际支付金额）
     */
    @TableField(value = "pay_amount")
    private BigDecimal payAmount;

    /**
     * 运费金额
     */
    @TableField(value = "freight_amount")
    private BigDecimal freightAmount;

    /**
     * 促销优化金额（促销价、满减、阶梯价）
     */
    @TableField(value = "promotion_amount")
    private BigDecimal promotionAmount;

    /**
     * 积分抵扣金额
     */
    @TableField(value = "integration_amount")
    private BigDecimal integrationAmount;

    /**
     * 优惠券抵扣金额
     */
    @TableField(value = "coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 管理员后台调整订单使用的折扣金额
     */
    @TableField(value = "discount_amount")
    private BigDecimal discountAmount;

    /**
     * 支付方式：0->未支付；1->支付宝；2->微信
     */
    @TableField(value = "pay_type")
    private Integer payType;

    /**
     * 订单来源：0->PC订单；1->app订单
     */
    @TableField(value = "source_type")
    private Integer sourceType;

    /**
     * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 订单类型：0->正常订单；1->秒杀订单
     */
    @TableField(value = "order_type")
    private Integer orderType;

    /**
     * 物流公司(配送方式)
     */
    @TableField(value = "delivery_company")
    private String deliveryCompany;

    /**
     * 物流单号
     */
    @TableField(value = "delivery_sn")
    private String deliverySn;

    /**
     * 自动确认时间（天）
     */
    @TableField(value = "auto_confirm_day")
    private Integer autoConfirmDay;

    /**
     * 可以获得的积分
     */
    @TableField(value = "integration")
    private Integer integration;

    /**
     * 可以活动的成长值
     */
    @TableField(value = "growth")
    private Integer growth;

    /**
     * 活动信息
     */
    @TableField(value = "promotion_info")
    private String promotionInfo;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    @TableField(value = "bill_type")
    private Integer billType;

    /**
     * 发票抬头
     */
    @TableField(value = "bill_header")
    private String billHeader;

    /**
     * 发票内容
     */
    @TableField(value = "bill_content")
    private String billContent;

    /**
     * 收票人电话
     */
    @TableField(value = "bill_receiver_phone")
    private String billReceiverPhone;

    /**
     * 收票人邮箱
     */
    @TableField(value = "bill_receiver_email")
    private String billReceiverEmail;

    /**
     * 收货人姓名
     */
    @TableField(value = "receiver_name")
    private String receiverName;

    /**
     * 收货人电话
     */
    @TableField(value = "receiver_phone")
    private String receiverPhone;

    /**
     * 收货人邮编
     */
    @TableField(value = "receiver_post_code")
    private String receiverPostCode;

    /**
     * 省份/直辖市
     */
    @TableField(value = "receiver_province")
    private String receiverProvince;

    /**
     * 城市
     */
    @TableField(value = "receiver_city")
    private String receiverCity;

    /**
     * 区
     */
    @TableField(value = "receiver_region")
    private String receiverRegion;

    /**
     * 详细地址
     */
    @TableField(value = "receiver_detail_address")
    private String receiverDetailAddress;

    /**
     * 订单备注
     */
    @TableField(value = "note")
    private String note;

    /**
     * 确认收货状态：0->未确认；1->已确认
     */
    @TableField(value = "confirm_status")
    private Integer confirmStatus;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    @TableField(value = "delete_status")
    private Integer deleteStatus;

    /**
     * 下单时使用的积分
     */
    @TableField(value = "use_integration")
    private Integer useIntegration;

    /**
     * 支付时间
     */
    @TableField(value = "payment_time")
    private Date paymentTime;

    /**
     * 发货时间
     */
    @TableField(value = "delivery_time")
    private Date deliveryTime;

    /**
     * 确认收货时间
     */
    @TableField(value = "receive_time")
    private Date receiveTime;

    /**
     * 评价时间
     */
    @TableField(value = "comment_time")
    private Date commentTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time")
    private Date modifyTime;

    private static final long serialVersionUID = 1L;
}