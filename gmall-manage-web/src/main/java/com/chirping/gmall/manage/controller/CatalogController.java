package com.chirping.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chirping.gmall.pojo.PmsBaseCatalog1;
import com.chirping.gmall.pojo.PmsBaseCatalog2;
import com.chirping.gmall.pojo.PmsBaseCatalog3;
import com.chirping.gmall.service.CatalogService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/11
 */
@RestController
@CrossOrigin
public class CatalogController {

    @Reference
    CatalogService catalogService;

    @PostMapping("getCatalog1")
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> catalog1s = catalogService.getCatalog1();
        return catalog1s;
    }

    @PostMapping("getCatalog2")
    public List<PmsBaseCatalog2> getCatalog2(@RequestParam("catalog1Id") String catalog1Id) {
        List<PmsBaseCatalog2> catalog2s = catalogService.getCatalog2(catalog1Id);
        return catalog2s;
    }

    @PostMapping("getCatalog3")
    public List<PmsBaseCatalog3> getCatalog3(@RequestParam("catalog2Id") String catalog2Id) {
        List<PmsBaseCatalog3> catalog3s = catalogService.getCatalog3(catalog2Id);
        return catalog3s;
    }
}
