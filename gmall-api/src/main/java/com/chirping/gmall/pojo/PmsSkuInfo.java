package com.chirping.gmall.pojo;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PmsSkuInfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    String id;
    @Column
    String productId;
    @Transient
    String spuId;
    @Column
    BigDecimal price;
    @Column
    String skuName;
    @Column
    BigDecimal weight;
    @Column
    String skuDesc;
    @Column
    String catalog3Id;
    @Column
    String skuDefaultImg;
    @Transient
    List<PmsSkuImage> skuImageList;
    @Transient
    List<PmsSkuAttrValue> skuAttrValueList;
    @Transient
    List<PmsSkuSaleAttrValue> skuSaleAttrValueList;

    public void setSpuId(String spuId) {
        this.spuId = spuId;
        this.productId=spuId;
    }

    private static final long serialVersionUID = 1L;
}
