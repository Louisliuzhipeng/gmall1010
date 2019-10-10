package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_sku_image")
public class PmsSkuImage implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "sku_id")
    private Long skuId;

    /**
     * 图片名称（冗余）
     */
    @TableField(value = "img_name")
    private String imgName;

    /**
     * 图片路径(冗余)
     */
    @TableField(value = "img_url")
    private String imgUrl;

    /**
     * 商品图片id
     */
    @TableField(value = "product_img_id")
    private Long productImgId;

    /**
     * 是否默认
     */
    @TableField(value = "is_default")
    private String isDefault;

    private static final long serialVersionUID = 1L;
}