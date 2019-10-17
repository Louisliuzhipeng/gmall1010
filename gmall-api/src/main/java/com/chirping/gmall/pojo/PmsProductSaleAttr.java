package com.chirping.gmall.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @author 15211
 */
@Data
public class PmsProductSaleAttr implements Serializable {

    @Id
    @Column
    String id;
    @Column
    String productId;
    @Column
    String saleAttrId;
    @Column
    String saleAttrName;
    @Transient
    List<PmsProductSaleAttrValue> spuSaleAttrValueList;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "PmsProductSaleAttr{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", saleAttrId='" + saleAttrId + '\'' +
                ", saleAttrName='" + saleAttrName + '\'' +
                ", spuSaleAttrValueList=" + spuSaleAttrValueList +
                '}';
    }
}
