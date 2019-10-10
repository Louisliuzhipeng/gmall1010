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
@TableName(value = "oms_cart_item")
public class OmsCartItem implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "product_id")
    private Long productId;

    @TableField(value = "product_sku_id")
    private Long productSkuId;

    @TableField(value = "member_id")
    private Long memberId;

    /**
     * 购买数量
     */
    @TableField(value = "quantity")
    private Integer quantity;

    /**
     * 添加到购物车的价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 销售属性1
     */
    @TableField(value = "sp1")
    private String sp1;

    /**
     * 销售属性2
     */
    @TableField(value = "sp2")
    private String sp2;

    /**
     * 销售属性3
     */
    @TableField(value = "sp3")
    private String sp3;

    /**
     * 商品主图
     */
    @TableField(value = "product_pic")
    private String productPic;

    /**
     * 商品名称
     */
    @TableField(value = "product_name")
    private String productName;

    /**
     * 商品副标题（卖点）
     */
    @TableField(value = "product_sub_title")
    private String productSubTitle;

    /**
     * 商品sku条码
     */
    @TableField(value = "product_sku_code")
    private String productSkuCode;

    /**
     * 会员昵称
     */
    @TableField(value = "member_nickname")
    private String memberNickname;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private Date createDate;

    /**
     * 修改时间
     */
    @TableField(value = "modify_date")
    private Date modifyDate;

    /**
     * 是否删除
     */
    @TableField(value = "delete_status")
    private Integer deleteStatus;

    /**
     * 商品分类
     */
    @TableField(value = "product_category_id")
    private Long productCategoryId;

    @TableField(value = "product_brand")
    private String productBrand;

    @TableField(value = "product_sn")
    private String productSn;

    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    @TableField(value = "product_attr")
    private String productAttr;

    private static final long serialVersionUID = 1L;
}