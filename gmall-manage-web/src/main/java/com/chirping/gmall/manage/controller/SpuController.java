package com.chirping.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyun.oss.internal.OSSUtils;
import com.chirping.gmall.pojo.PmsProductInfo;
import com.chirping.gmall.pojo.PmsProductSaleAttr;
import com.chirping.gmall.service.SpuService;
import com.chirping.gmall.web.util.Ossutil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/12
 */

@RestController
@CrossOrigin
public class SpuController {

    @Reference
    SpuService spuService;

    @GetMapping("spuList")
    public List<PmsProductInfo> spuList(@RequestParam("catalog3Id") String catalog3Id) {
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        return pmsProductInfos;
    }

    @RequestMapping("saveSpuInfo")
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        String success = spuService.saveSpuInfo(pmsProductInfo);
        return success;
    }

    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile, String type) {
        String success = null;
        try {
            success = Ossutil.saveimg(type, multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }
}
