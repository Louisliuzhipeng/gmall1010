package com.chirping.gmall.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 15211
 */
@Data
public class PmsBaseCatalog1 implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String name;
    @Transient
    private List<PmsBaseCatalog2> catalog2s;

    private static final long serialVersionUID = 1L;
}

