package com.chirping.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chirping.gmall.mapper.PmsBaseCatalog1Mapper;
import com.chirping.gmall.mapper.PmsBaseCatalog2Mapper;
import com.chirping.gmall.mapper.PmsBaseCatalog3Mapper;
import com.chirping.gmall.pojo.PmsBaseCatalog1;
import com.chirping.gmall.pojo.PmsBaseCatalog2;
import com.chirping.gmall.pojo.PmsBaseCatalog3;
import com.chirping.gmall.service.CatalogService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/11
 */
@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    @Resource
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    @Resource
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    @Resource
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseCatalog1Mapper.selectAll();
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        PmsBaseCatalog2 pmsBaseCatalog2 = new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(catalog1Id);
        return pmsBaseCatalog2Mapper.select(pmsBaseCatalog2);
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 pmsBaseCatalog3 = new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(catalog2Id);
        return pmsBaseCatalog3Mapper.select(pmsBaseCatalog3);
    }
}
