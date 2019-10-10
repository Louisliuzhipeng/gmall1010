package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_base_catalog2")
public class PmsBaseCatalog2 implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 二级分类名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 一级分类编号
     */
    @TableField(value = "catalog1_id")
    private Integer catalog1Id;

    private static final long serialVersionUID = 1L;
}