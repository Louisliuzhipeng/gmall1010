package com.chirping.gmall.service;

import com.chirping.gmall.pojo.PmsProductImage;
import com.chirping.gmall.pojo.PmsProductInfo;
import com.chirping.gmall.pojo.PmsProductSaleAttr;
import com.chirping.gmall.pojo.PmsSkuInfo;

import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/12
 */
public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo) throws Exception;

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    String saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId);
}
