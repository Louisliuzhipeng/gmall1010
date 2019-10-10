package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_sku_attr_value")
public class PmsSkuAttrValue implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性id（冗余)
     */
    @TableField(value = "attr_id")
    private Long attrId;

    /**
     * 属性值id
     */
    @TableField(value = "value_id")
    private Long valueId;

    /**
     * skuid
     */
    @TableField(value = "sku_id")
    private Long skuId;

    private static final long serialVersionUID = 1L;
}