package com.chirping.gmall.service;

import com.chirping.gmall.pojo.PmsSearchParam;
import com.chirping.gmall.pojo.PmsSearchSkuInfo;

import java.io.IOException;
import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/28
 */
public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);
}
