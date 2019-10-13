package com.chirping.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chirping.gmall.pojo.PmsBaseAttrInfo;
import com.chirping.gmall.pojo.PmsBaseAttrValue;
import com.chirping.gmall.pojo.PmsBaseSaleAttr;
import com.chirping.gmall.pojo.PmsProductSaleAttr;
import com.chirping.gmall.service.AttrService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/11
 */
@RestController
@CrossOrigin
public class AttrController {

    @Reference
    AttrService attrService;

    @GetMapping("attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(@RequestParam("catalog3Id") String catalog3Id) {
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }

    @PostMapping("getAttrValueList")
    public List<PmsBaseAttrValue> getAttrValueList(@RequestParam("attrId") String attrId) {
        List<PmsBaseAttrValue> pmsBaseAttrValues = attrService.getAttrValueList(attrId);
        return pmsBaseAttrValues;
    }

    @RequestMapping("saveAttrInfo")
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo) {
        String success = attrService.saveAttrInfo(pmsBaseAttrInfo);
        return success;
    }

    @PostMapping("baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsProductSaleAttrs = attrService.baseSaleAttrList();
        return pmsProductSaleAttrs;
    }
}
