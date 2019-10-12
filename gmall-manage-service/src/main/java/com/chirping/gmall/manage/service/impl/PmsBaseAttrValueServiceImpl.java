package com.chirping.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.chirping.gmall.mapper.PmsBaseAttrValueMapper;
import com.chirping.gmall.pojo.PmsBaseAttrValue;
import com.chirping.gmall.service.PmsBaseAttrValueService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘志鹏
 * @date $date$
 */
@Service
public class PmsBaseAttrValueServiceImpl implements PmsBaseAttrValueService {

    @Resource
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        return pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
    }
}
