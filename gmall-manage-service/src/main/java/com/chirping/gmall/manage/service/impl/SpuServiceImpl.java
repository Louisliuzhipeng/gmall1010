package com.chirping.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chirping.gmall.mapper.PmsProductInfoMapper;
import com.chirping.gmall.mapper.PmsProductSaleAttrMapper;
import com.chirping.gmall.pojo.PmsBaseAttrInfo;
import com.chirping.gmall.pojo.PmsProductInfo;
import com.chirping.gmall.pojo.PmsProductSaleAttr;
import com.chirping.gmall.service.SpuService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/12
 */

@Service
public class SpuServiceImpl implements SpuService {

    @Resource
    PmsProductInfoMapper pmsProductInfoMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        return pmsProductInfoMapper.select(pmsProductInfo);
    }

    @Override
    public String saveSpuInfo(PmsProductInfo pmsProductInfo) {
        return null;
    }
}
