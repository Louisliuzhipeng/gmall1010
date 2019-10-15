package com.chirping.gmall.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author 15211
 */
@Data
public class PmsSkuSaleAttrValue implements Serializable {

    @Id
    @Column
    String id;
    @Column
    String skuId;
    @Column
    String saleAttrId;
    @Column
    String saleAttrValueId;
    @Column
    String saleAttrName;
    @Column
    String saleAttrValueName;

    private static final long serialVersionUID = 1L;
}
