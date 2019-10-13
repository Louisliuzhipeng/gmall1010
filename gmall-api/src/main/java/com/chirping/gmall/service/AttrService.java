package com.chirping.gmall.service;

import com.chirping.gmall.pojo.PmsBaseAttrInfo;
import com.chirping.gmall.pojo.PmsBaseAttrValue;
import com.chirping.gmall.pojo.PmsBaseSaleAttr;

import java.util.List;

/**
 * @author 刘志鹏
 * @date $date$
 */
public interface AttrService {

    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();
}
