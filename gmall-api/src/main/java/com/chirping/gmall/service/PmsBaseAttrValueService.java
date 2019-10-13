package com.chirping.gmall.service;

import com.chirping.gmall.pojo.PmsBaseAttrValue;

import java.util.List;

/**
 * @author 刘志鹏
 * @date $date$
 */
public interface PmsBaseAttrValueService {
    List<PmsBaseAttrValue> getAttrValueList(String attrId);
}
