package com.chirping.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chirping.gmall.pojo.PmsSearchParam;
import com.chirping.gmall.pojo.PmsSearchSkuInfo;
import com.chirping.gmall.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/28
 */
@Controller
public class SearchController {

    @Reference
    SearchService searchService;

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam, Model model) {
        List<PmsSearchSkuInfo> searchSkuInfos = searchService.list(pmsSearchParam);
        for (PmsSearchSkuInfo pmsSearchSkuInfo : searchSkuInfos) {
            System.out.println(pmsSearchSkuInfo);
        }
        model.addAttribute("skuLsInfoList", searchSkuInfos);
        return "list";
    }

}
