package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_product_sale_attr_value")
public class PmsProductSaleAttrValue implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 销售属性id
     */
    @TableField(value = "sale_attr_id")
    private Long saleAttrId;

    /**
     * 销售属性值名称
     */
    @TableField(value = "sale_attr_value_name")
    private String saleAttrValueName;

    private static final long serialVersionUID = 1L;
}