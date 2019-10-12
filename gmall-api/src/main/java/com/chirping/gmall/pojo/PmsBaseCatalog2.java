package com.chirping.gmall.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 15211
 */
@Data
public class PmsBaseCatalog2 implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String name;
    @Column
    private String catalog1Id;

    @Transient
    private List<PmsBaseCatalog3> catalog3List;

    private static final long serialVersionUID = 1L;
}
