package com.chirping.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.StringUtils;
import com.chirping.gmall.mapper.*;
import com.chirping.gmall.pojo.*;
import com.chirping.gmall.service.SpuService;
import com.chirping.gmall.util.RedisUtil;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 刘志鹏
 * @date 2019/10/12
 */

@Service
@Transactional
public class SpuServiceImpl implements SpuService {

    @Resource
    PmsProductInfoMapper pmsProductInfoMapper;
    @Resource
    PmsProductImageMapper pmsProductImageMapper;
    @Resource
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Resource
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Resource
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Resource
    PmsSkuImageMapper pmsSkuImageMapper;
    @Resource
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Resource
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Resource
    RedisUtil redisUtil;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        return pmsProductInfoMapper.select(pmsProductInfo);
    }

    @Override
    public void saveSpuInfo(PmsProductInfo pmsProductInfo) throws Exception {
        // 保存商品信息
        pmsProductInfoMapper.insertSelective(pmsProductInfo);
        // 生成商品主键
        String productId = pmsProductInfo.getId();
        // 保存商品图片信息
        List<PmsProductImage> spuImageList = pmsProductInfo.getSpuImageList();
        for (PmsProductImage pmsProductImage : spuImageList) {
            pmsProductImage.setProductId(productId);
            pmsProductImageMapper.insertSelective(pmsProductImage);
        }
        // 保存销售属性信息
        List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {
            pmsProductSaleAttr.setProductId(productId);
            pmsProductSaleAttrMapper.insertSelective(pmsProductSaleAttr);
            // 保存销售属性值
            List<PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : spuSaleAttrValueList) {
                pmsProductSaleAttrValue.setProductId(productId);
                pmsProductSaleAttrValueMapper.insertSelective(pmsProductSaleAttrValue);
            }
        }
    }

    @Override
    public List<PmsProductImage> spuImageList(String spuId) {
        PmsProductImage pmsProductImage = new PmsProductImage();
        pmsProductImage.setProductId(spuId);
        return pmsProductImageMapper.select(pmsProductImage);
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.select(pmsProductSaleAttr);

        for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrs) {
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = new ArrayList<>();

            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setProductId(spuId);
            pmsProductSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
            pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue);
            productSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
        }
        return pmsProductSaleAttrs;
    }

    @Override
    public String saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        try {
            pmsSkuInfoMapper.insertSelective(pmsSkuInfo);

            for (PmsSkuImage pmsSkuImage : pmsSkuInfo.getSkuImageList()) {
                pmsSkuImage.setSkuId(pmsSkuInfo.getId());
                pmsSkuImageMapper.insertSelective(pmsSkuImage);
            }

            for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuInfo.getSkuAttrValueList()) {
                pmsSkuAttrValue.setSkuId(pmsSkuInfo.getId());
                pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
            }
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuInfo.getSkuSaleAttrValueList()) {
                pmsSkuSaleAttrValue.setSkuId(pmsSkuInfo.getId());
                pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
            }
            return "保存成功";
        } catch (Exception e) {
            return "保存出錯";
        }
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String skuId) {

        /*PmsProductSaleAttr pmsProductSaleAttr=new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(productId);
        List<PmsProductSaleAttr> saleAttrs = pmsProductSaleAttrMapper.select(pmsProductSaleAttr);

        for (PmsProductSaleAttr productSaleAttr : saleAttrs) {

            PmsProductSaleAttrValue pmsProductSaleAttrValue=new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
            pmsProductSaleAttrValue.setProductId(productId);

            productSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue));
        }*/

        List<PmsProductSaleAttr> saleAttrs = pmsProductSaleAttrMapper.selectspuSaleAttrListCheckBySku(productId, skuId);

        return saleAttrs;
    }

    @Override
    public List<PmsSkuInfo> getspuSaleAttrListCheckBySku(String productId) {
        return pmsSkuInfoMapper.selectspuSaleAttrListCheckBySku(productId);
    }

    public PmsSkuInfo getSkuInfoByIdFromDb(String skuId) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuInfo.getId());
        skuInfo.setSkuImageList(pmsSkuImageMapper.select(pmsSkuImage));
        return skuInfo;
    }

    @Override
    public PmsSkuInfo getSkuInfoById(String skuId, String ip) {
        System.out.println(ip);
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        //获取jedis
        Jedis jedis = redisUtil.getJedis();
        String skuKey = "sku:" + skuId + ":info";
        //查询缓存
        String skuJson = jedis.get(skuKey);
        if (StringUtils.isNotBlank(skuJson)) {
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        } else {
            String token = UUID.randomUUID().toString();
            String ok = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10000);
            if (StringUtils.isNotBlank(ok) && ok.equals("OK")) {
                pmsSkuInfo = getSkuInfoByIdFromDb(skuId);
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (pmsSkuInfo != null) {
                    jedis.set("sku:" + skuId + ":info", JSON.toJSONString(pmsSkuInfo));
                } else {
                    jedis.setex("sku:" + skuId + ":info", 60 * 3, JSON.toJSONString(""));
                }
                String locktoken = jedis.get("sku:" + skuId + ":lock");
                if (StringUtils.isNotBlank(locktoken) && locktoken.equals(token)) {
                    jedis.del("sku:" + skuId + ":lock");
                }
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getSkuInfoById(skuId, ip);
            }
        }
        //关闭jedis
        jedis.close();
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getAllSku() {
        List<PmsSkuInfo> skuInfos = pmsSkuInfoMapper.selectAll();
        for (PmsSkuInfo skuInfo : skuInfos) {
            String skuId = skuInfo.getId();

            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(skuId);
            List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);
            skuInfo.setSkuAttrValueList(pmsSkuAttrValues);
        }
        return skuInfos;
    }
}
