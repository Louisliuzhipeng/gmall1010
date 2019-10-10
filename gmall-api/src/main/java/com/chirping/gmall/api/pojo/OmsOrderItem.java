package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
@TableName(value = "oms_order_item")
public class OmsOrderItem implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 订单编号
     */
    @TableField(value = "order_sn")
    private String orderSn;

    @TableField(value = "product_id")
    private Long productId;

    @TableField(value = "product_pic")
    private String productPic;

    @TableField(value = "product_name")
    private String productName;

    @TableField(value = "product_brand")
    private String productBrand;

    @TableField(value = "product_sn")
    private String productSn;

    /**
     * 销售价格
     */
    @TableField(value = "product_price")
    private BigDecimal productPrice;

    /**
     * 购买数量
     */
    @TableField(value = "product_quantity")
    private Integer productQuantity;

    /**
     * 商品sku编号
     */
    @TableField(value = "product_sku_id")
    private Long productSkuId;

    /**
     * 商品sku条码
     */
    @TableField(value = "product_sku_code")
    private String productSkuCode;

    /**
     * 商品分类id
     */
    @TableField(value = "product_category_id")
    private Long productCategoryId;

    /**
     * 商品的销售属性
     */
    @TableField(value = "sp1")
    private String sp1;

    @TableField(value = "sp2")
    private String sp2;

    @TableField(value = "sp3")
    private String sp3;

    /**
     * 商品促销名称
     */
    @TableField(value = "promotion_name")
    private String promotionName;

    /**
     * 商品促销分解金额
     */
    @TableField(value = "promotion_amount")
    private BigDecimal promotionAmount;

    /**
     * 优惠券优惠分解金额
     */
    @TableField(value = "coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 积分优惠分解金额
     */
    @TableField(value = "integration_amount")
    private BigDecimal integrationAmount;

    /**
     * 该商品经过优惠后的分解金额
     */
    @TableField(value = "real_amount")
    private BigDecimal realAmount;

    @TableField(value = "gift_integration")
    private Integer giftIntegration;

    @TableField(value = "gift_growth")
    private Integer giftGrowth;

    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    @TableField(value = "product_attr")
    private String productAttr;

    private static final long serialVersionUID = 1L;
}