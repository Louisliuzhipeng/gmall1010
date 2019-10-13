package com.chirping.gmall.manage.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.chirping.gmall.mapper.PmsBaseAttrInfoMapper;
import com.chirping.gmall.mapper.PmsBaseAttrValueMapper;
import com.chirping.gmall.mapper.PmsBaseSaleAttrMapper;
import com.chirping.gmall.mapper.PmsProductSaleAttrMapper;
import com.chirping.gmall.pojo.PmsBaseAttrInfo;
import com.chirping.gmall.pojo.PmsBaseAttrValue;
import com.chirping.gmall.pojo.PmsBaseSaleAttr;
import com.chirping.gmall.pojo.PmsProductSaleAttr;
import com.chirping.gmall.service.AttrService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/11
 */
@Service
public class AttrServiceImpl implements AttrService {

    @Resource
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Resource
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Resource
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        return pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        return pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }

    @Override
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        try {
            String id = pmsBaseAttrInfo.getId();
            if (StringUtils.isBlank(id)) {
                //保存属性
                pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
                //提取属性值集合
                List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                //保存属性值
                for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                    pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                    pmsBaseAttrValueMapper.insert(pmsBaseAttrValue);
                }
            } else {
                Example example = new Example(PmsBaseAttrInfo.class);
                example.createCriteria().andEqualTo("id", pmsBaseAttrInfo.getId());
                //修改属性
                pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo, example);

                PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.delete(pmsBaseAttrValue);

                //修改属性值
                List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrInfo.getAttrValueList();
                for (PmsBaseAttrValue baseAttrValue : pmsBaseAttrValues) {
                    baseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                    pmsBaseAttrValueMapper.insertSelective(baseAttrValue);
                }
            }
        } catch (Exception e) {
            return "保存失败";
        }
        return "保存成功";
    }
}
