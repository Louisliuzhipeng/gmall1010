package com.chirping.gmall.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author 15211
 */
@Data
public class PmsProductSaleAttrValue implements Serializable {

    @Id
    @Column
    String id;
    @Column
    String productId;
    @Column
    String saleAttrId;
    @Column
    String saleAttrValueName;
    @Transient
    String isChecked;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "PmsProductSaleAttrValue{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", saleAttrId='" + saleAttrId + '\'' +
                ", saleAttrValueName='" + saleAttrValueName + '\'' +
                ", isChecked='" + isChecked + '\'' +
                '}';
    }
}
