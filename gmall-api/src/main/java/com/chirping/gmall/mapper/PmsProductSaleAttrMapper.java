package com.chirping.gmall.mapper;

import com.chirping.gmall.pojo.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 刘志鹏
 * @date $date$
 */
public interface PmsProductSaleAttrMapper extends Mapper<PmsProductSaleAttr> {
    List<PmsProductSaleAttr> selectspuSaleAttrListCheckBySku(@Param("productId") String productId,@Param("skuId") String skuId);
}
