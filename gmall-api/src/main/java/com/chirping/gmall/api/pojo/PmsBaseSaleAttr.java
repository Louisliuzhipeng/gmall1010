package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_base_sale_attr")
public class PmsBaseSaleAttr implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 销售属性名称
     */
    @TableField(value = "name")
    private String name;

    private static final long serialVersionUID = 1L;
}