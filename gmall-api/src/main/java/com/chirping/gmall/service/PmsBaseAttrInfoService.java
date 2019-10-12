package com.chirping.gmall.service;

import com.chirping.gmall.pojo.PmsBaseAttrInfo;

import java.util.List;

/**
 * @author 刘志鹏
 * @date $date$
 */
public interface PmsBaseAttrInfoService {

    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
}
