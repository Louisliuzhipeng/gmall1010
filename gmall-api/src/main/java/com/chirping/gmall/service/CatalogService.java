package com.chirping.gmall.service;

import com.chirping.gmall.pojo.PmsBaseCatalog1;
import com.chirping.gmall.pojo.PmsBaseCatalog2;
import com.chirping.gmall.pojo.PmsBaseCatalog3;

import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/11
 */
public interface CatalogService {
    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);

    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);
}
