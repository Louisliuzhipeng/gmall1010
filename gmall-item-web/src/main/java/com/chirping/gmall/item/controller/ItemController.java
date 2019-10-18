package com.chirping.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.chirping.gmall.pojo.*;
import com.chirping.gmall.service.ItemService;
import com.chirping.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘志鹏
 * @date 2019/10/14
 */
@Controller
public class ItemController {

    @Reference
    ItemService itemService;
    @Reference
    SpuService spuService;

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, Model model){
        PmsSkuInfo pmsSkuInfo=itemService.getSkuInfoById(skuId);
        model.addAttribute("skuInfo",pmsSkuInfo);

        List<PmsProductSaleAttr> pmsProductSaleAttrs=spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(),pmsSkuInfo.getId());
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrs) {
            System.out.println(pmsProductSaleAttr);
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttr.getSpuSaleAttrValueList()) {
                System.out.println(pmsProductSaleAttrValue);
            }
        }
        model.addAttribute("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);

        // 查询当前sku的spu的其他sku的集合的hash表
        Map<String, String> skuSaleAttrHash = new HashMap<>();
        List<PmsSkuInfo> pmsSkuInfos = spuService.getspuSaleAttrListCheckBySku(pmsSkuInfo.getProductId());

        for (PmsSkuInfo skuInfo : pmsSkuInfos) {
            String k = "";
            String v = skuInfo.getId();
            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                k += pmsSkuSaleAttrValue.getSaleAttrValueId() + "|";
            }
            skuSaleAttrHash.put(k,v);
        }

        // 将sku的销售属性hash表放到页面
        String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);
        model.addAttribute("valuesSkuJson",skuSaleAttrHashJsonStr);
        return "item";
    }

}
