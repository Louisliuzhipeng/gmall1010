package com.chirping.gmall.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author 15211
 */
@Data
public class PmsBaseSaleAttr implements Serializable {

    @Id
    @Column
    String id;
    @Column
    String name;

    private static final long serialVersionUID = 1L;
}