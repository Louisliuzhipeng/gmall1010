package com.chirping.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chirping.gmall.pojo.PmsBaseAttrInfo;
import com.chirping.gmall.pojo.PmsBaseAttrValue;
import com.chirping.gmall.service.PmsBaseAttrInfoService;
import com.chirping.gmall.service.PmsBaseAttrValueService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/11
 */
@RestController
@CrossOrigin
public class AttrController {

    @Reference
    PmsBaseAttrInfoService pmsBaseAttrInfoService;
    @Reference
    PmsBaseAttrValueService pmsBaseAttrValueService;

    @GetMapping("attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(@RequestParam("catalog3Id") String catalog3Id) {
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }

    @PostMapping("getAttrValueList")
    public List<PmsBaseAttrValue> getAttrValueList(@RequestParam("attrId") String attrId) {
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueService.getAttrValueList(attrId);
        return pmsBaseAttrValues;
    }

    @RequestMapping("saveAttrInfo")
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo) {
        String success = pmsBaseAttrInfoService.saveAttrInfo(pmsBaseAttrInfo);
        return success;
    }
}
