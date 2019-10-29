package com.chirping.gmall.cart.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.StringUtils;
import com.chirping.gmall.pojo.OmsCartItem;
import com.chirping.gmall.pojo.PmsSkuInfo;
import com.chirping.gmall.service.CartService;
import com.chirping.gmall.service.SpuService;
import com.chirping.gmall.web.util.CookieUtil;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/29
 */
@Controller
public class CartController {

    @Reference
    CartService cartService;
    @Reference
    SpuService spuService;

    @RequestMapping("addToCart")
    public String addToCart(String skuId, int quantity, HttpServletRequest request, HttpServletResponse response) {
        //查询商品信息
        PmsSkuInfo skuInfoById = spuService.getSkuInfoById(skuId, "");

        //将商品封装成购物车信息
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setModifyDate(new Date());
        omsCartItem.setDeleteStatus(0);
        omsCartItem.setPrice(skuInfoById.getPrice());
        omsCartItem.setProductAttr("");
        omsCartItem.setProductBrand("");
        omsCartItem.setProductCategoryId(skuInfoById.getCatalog3Id());
        omsCartItem.setProductId(skuInfoById.getProductId());
        omsCartItem.setProductName(skuInfoById.getSkuName());
        omsCartItem.setProductPic(skuInfoById.getSkuDefaultImg());
        omsCartItem.setProductSkuCode("11111111");
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setQuantity(quantity);

        String memberId = "";

        if (StringUtils.isNotBlank(memberId)) {
            //用户没有登录
            List<OmsCartItem> omsCartItems = new ArrayList<>();
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if (StringUtils.isNotBlank(cartListCookie)) {
                omsCartItems.add(omsCartItem);
            } else {
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
                //判断添加的商品在cookie中存在吗
                boolean exist = ifCartexist(omsCartItems, omsCartItem);
                if (exist) {
                    //有就更新数据
                    for (OmsCartItem cartItem : omsCartItems) {
                        if (cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())) {
                            cartItem.setQuantity(cartItem.getQuantity() + omsCartItem.getQuantity());
                            cartItem.setPrice(cartItem.getPrice().add(omsCartItem.getPrice()));
                        }
                    }
                } else {
                    //没有就,添加到当前的购物车
                    omsCartItems.add(omsCartItem);
                }
            }
            CookieUtil.setCookie(request, response, "cartListCookie", JSON.toJSONString(omsCartItems), 60 * 60 * 72, true);
        } else {
            //用户已登录
            List<OmsCartItem> omsCartItems = new ArrayList<>();
        }
        //cartService.getPmsBaseSkuInfoCart(skuId);
        return "success";
    }

    private boolean ifCartexist(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {
        for (OmsCartItem cartItem : omsCartItems) {
            String productSkuId = cartItem.getProductSkuId();
            if (productSkuId.equals(omsCartItem.getProductSkuId())) {
                return false;
            }
        }
        return true;
    }
}
