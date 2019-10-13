package com.chirping.gmall.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author 15211
 */
@Data
public class PmsProductImage implements Serializable {

    @Column
    @Id
    private String id;
    @Column
    private String productId;
    @Column
    private String imgName;
    @Column
    private String imgUrl;

    private static final long serialVersionUID = 1L;
}