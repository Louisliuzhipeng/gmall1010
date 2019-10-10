package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_base_catalog3")
public class PmsBaseCatalog3 implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 三级分类名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 二级分类编号
     */
    @TableField(value = "catalog2_id")
    private Long catalog2Id;

    private static final long serialVersionUID = 1L;
}