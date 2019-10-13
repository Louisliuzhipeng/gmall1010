package com.chirping.gmall.service;

import com.chirping.gmall.pojo.PmsProductInfo;

import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/12
 */
public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    String saveSpuInfo(PmsProductInfo pmsProductInfo);
}
