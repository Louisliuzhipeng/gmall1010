package com.chirping.gmall.pojo;

import lombok.Data;

import java.util.Date;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Id;

/**
 * @author 刘志鹏
 * @date 2019/10/24
 */
@Data
public class PmsSearchSkuInfo {
    @Id
    private String id;
    private String skuName;
    private String skuDesc;
    private String catalog3Id;
    private BigDecimal price;
    private String skuDefaultImg;
    private double hotScore;
    private String productId;
    private List<PmsSkuAttrValue> skuAttrValueList;
}
