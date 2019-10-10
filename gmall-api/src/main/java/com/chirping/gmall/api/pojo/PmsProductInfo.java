package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_product_info")
public class PmsProductInfo implements Serializable {
    /**
     * 商品id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    @TableField(value = "product_name")
    private String productName;

    /**
     * 商品描述(后台简述）
     */
    @TableField(value = "description")
    private String description;

    /**
     * 三级分类id
     */
    @TableField(value = "catalog3_id")
    private Long catalog3Id;

    /**
     * 品牌id
     */
    @TableField(value = "tm_id")
    private Long tmId;

    private static final long serialVersionUID = 1L;
}