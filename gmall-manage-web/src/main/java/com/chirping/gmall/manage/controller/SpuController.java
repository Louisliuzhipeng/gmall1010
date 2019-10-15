package com.chirping.gmall.manage.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.chirping.gmall.manage.util.PmsUploadUtil;
import com.chirping.gmall.pojo.PmsProductImage;
import com.chirping.gmall.pojo.PmsProductInfo;
import com.chirping.gmall.pojo.PmsProductSaleAttr;
import com.chirping.gmall.pojo.PmsSkuInfo;
import com.chirping.gmall.service.SpuService;
import com.chirping.gmall.manage.util.Ossutil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
        try {
            spuService.saveSpuInfo(pmsProductInfo);
            return "保存成功";
        }catch (Exception e){
            return "保存失败";
        }
    }

    @GetMapping("spuSaleAttrList")
    public List<PmsProductSaleAttr> spuSaleAttrList(@RequestParam("spuId") String spuId){
        List<PmsProductSaleAttr> pmsProductSaleAttrs=spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrs;
    }

    @GetMapping("spuImageList")
    public List<PmsProductImage> spuImageList(@RequestParam("spuId") String spuId) {
        List<PmsProductImage> pmsProductImages=spuService.spuImageList(spuId);
        return pmsProductImages;
    }

    @RequestMapping("saveSkuInfo")
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){

       String skuDefaultImg=pmsSkuInfo.getSkuDefaultImg();
       if (StringUtils.isBlank(skuDefaultImg)){
           pmsSkuInfo.setSkuDefaultImg(pmsSkuInfo.getSkuImageList().get(0).getImgUrl());
       }
        String success=spuService.saveSkuInfo(pmsSkuInfo);
        return success;
    }

    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile, String type) {
        String success = null;
        /*try {
            success = Ossutil.saveimg(type, multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        success=PmsUploadUtil.uploadImage(multipartFile);
        System.out.println(success);
        return success;
    }
}
