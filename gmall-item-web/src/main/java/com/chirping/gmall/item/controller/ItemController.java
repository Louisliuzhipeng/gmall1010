package com.chirping.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chirping.gmall.pojo.*;
import com.chirping.gmall.service.ItemService;
import com.chirping.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

        List<PmsProductSaleAttr> pmsProductSaleAttrs=spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId());
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductSaleAttrs) {
            System.out.println(pmsProductSaleAttr);
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttr.getSpuSaleAttrValueList()) {
                System.out.println(pmsProductSaleAttrValue);
            }
        }
        model.addAttribute("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);
        return "item";
    }

}
