package com.chirping.gmall.pojo;

import java.io.Serializable;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author 15211
 */
@Data
public class PmsBaseCatalog3 implements Serializable {
    /**
     * 编号
     */
    @Id
    private String id;

    /**
     * 三级分类名称
     */
    @Column
    private String name;

    /**
     * 二级分类编号
     */
    @Column
    private String catalog2Id;

    private static final long serialVersionUID = 1L;
}

