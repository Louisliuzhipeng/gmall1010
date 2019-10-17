package com.chirping.gmall.item.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chirping.gmall.mapper.*;
import com.chirping.gmall.pojo.PmsSkuAttrValue;
import com.chirping.gmall.pojo.PmsSkuImage;
import com.chirping.gmall.pojo.PmsSkuInfo;
import com.chirping.gmall.pojo.PmsSkuSaleAttrValue;
import com.chirping.gmall.service.ItemService;

import javax.annotation.Resource;

/**
 * @author 刘志鹏
 * @date 2019/10/14
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Resource
    PmsSkuImageMapper pmsSkuImageMapper;
    @Resource
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Resource
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Override
    public PmsSkuInfo getSkuInfoById(String skuId) {
        PmsSkuInfo pmsSkuInfo=new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo=pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        PmsSkuImage pmsSkuImage=new PmsSkuImage();
        pmsSkuImage.setSkuId(skuInfo.getId());

        skuInfo.setSkuImageList(pmsSkuImageMapper.select(pmsSkuImage));

        PmsSkuAttrValue pmsSkuAttrValue=new PmsSkuAttrValue();
        pmsSkuAttrValue.setSkuId(skuInfo.getId());

        skuInfo.setSkuAttrValueList(pmsSkuAttrValueMapper.select(pmsSkuAttrValue));

        PmsSkuSaleAttrValue pmsSkuSaleAttrValue=new PmsSkuSaleAttrValue();
        pmsSkuSaleAttrValue.setSkuId(skuInfo.getId());

        skuInfo.setSkuSaleAttrValueList(pmsSkuSaleAttrValueMapper.select(pmsSkuSaleAttrValue));
        return skuInfo;
    }
}
