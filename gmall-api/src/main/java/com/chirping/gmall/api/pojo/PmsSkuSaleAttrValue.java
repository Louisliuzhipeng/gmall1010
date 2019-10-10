package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_sku_sale_attr_value")
public class PmsSkuSaleAttrValue implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 库存单元id
     */
    @TableField(value = "sku_id")
    private Long skuId;

    /**
     * 销售属性id（冗余)
     */
    @TableField(value = "sale_attr_id")
    private Long saleAttrId;

    /**
     * 销售属性值id
     */
    @TableField(value = "sale_attr_value_id")
    private Long saleAttrValueId;

    /**
     * 销售属性名称(冗余)
     */
    @TableField(value = "sale_attr_name")
    private String saleAttrName;

    /**
     * 销售属性值名称(冗余)
     */
    @TableField(value = "sale_attr_value_name")
    private String saleAttrValueName;

    private static final long serialVersionUID = 1L;
}