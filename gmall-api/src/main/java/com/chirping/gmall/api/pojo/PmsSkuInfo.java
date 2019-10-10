package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_sku_info")
public class PmsSkuInfo implements Serializable {
    /**
     * 库存id(itemID)
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 价格
     */
    @TableField(value = "price")
    private Double price;

    /**
     * sku名称
     */
    @TableField(value = "sku_name")
    private String skuName;

    /**
     * 商品规格描述
     */
    @TableField(value = "sku_desc")
    private String skuDesc;

    @TableField(value = "weight")
    private Double weight;

    /**
     * 品牌(冗余)
     */
    @TableField(value = "tm_id")
    private Long tmId;

    /**
     * 三级分类id（冗余)
     */
    @TableField(value = "catalog3_id")
    private Long catalog3Id;

    /**
     * 默认显示图片(冗余)
     */
    @TableField(value = "sku_default_img")
    private String skuDefaultImg;

    private static final long serialVersionUID = 1L;
}