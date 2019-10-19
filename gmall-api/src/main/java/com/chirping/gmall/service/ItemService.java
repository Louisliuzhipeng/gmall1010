package com.chirping.gmall.service;

import com.chirping.gmall.pojo.PmsSkuInfo;

/**
 * @author 刘志鹏
 * @date $date$
 */
public interface ItemService {
    PmsSkuInfo getSkuInfoById(String skuId,String ip);
}
