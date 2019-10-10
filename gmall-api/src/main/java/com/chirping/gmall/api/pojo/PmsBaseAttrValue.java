package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_base_attr_value")
public class PmsBaseAttrValue implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性值名称
     */
    @TableField(value = "value_name")
    private String valueName;

    /**
     * 属性id
     */
    @TableField(value = "attr_id")
    private Long attrId;

    /**
     * 启用：1 停用：0 1
     */
    @TableField(value = "is_enabled")
    private String isEnabled;

    private static final long serialVersionUID = 1L;
}