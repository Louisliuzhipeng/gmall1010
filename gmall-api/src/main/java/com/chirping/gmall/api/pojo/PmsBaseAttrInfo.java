package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_base_attr_info")
public class PmsBaseAttrInfo implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性名称
     */
    @TableField(value = "attr_name")
    private String attrName;

    @TableField(value = "catalog3_id")
    private Long catalog3Id;

    /**
     * 启用：1 停用：0
     */
    @TableField(value = "is_enabled")
    private String isEnabled;

    private static final long serialVersionUID = 1L;
}