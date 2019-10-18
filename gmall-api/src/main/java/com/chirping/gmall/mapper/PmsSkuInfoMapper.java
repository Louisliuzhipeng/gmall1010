package com.chirping.gmall.mapper;

import com.chirping.gmall.pojo.PmsSkuInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/13
 */
public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {
    List<PmsSkuInfo> selectspuSaleAttrListCheckBySku(@Param("productId") String productId);
}
