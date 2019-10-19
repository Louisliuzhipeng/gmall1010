package com.chirping.gmall.item.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.StringUtils;
import com.chirping.gmall.mapper.*;
import com.chirping.gmall.pojo.PmsSkuImage;
import com.chirping.gmall.pojo.PmsSkuInfo;
import com.chirping.gmall.service.ItemService;
import com.chirping.gmall.util.RedisUtil;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author 刘志鹏
 * @date 2019/10/14
 */
@Service
public class ItemServiceImpl implements ItemService {

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
}
