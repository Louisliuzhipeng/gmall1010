package com.chirping.gmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chirping.gmall.annotations.LoginRequired;
import com.chirping.gmall.pojo.OmsCartItem;
import com.chirping.gmall.pojo.OmsOrderItem;
import com.chirping.gmall.pojo.UmsMemberReceiveAddress;
import com.chirping.gmall.service.CartService;
import com.chirping.gmall.service.UmsMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘志鹏
 * @date $date$
 */
@Controller
public class OrderController {

    @Reference
    UmsMemberService umsMemberService;
    @Reference
    CartService cartService;

    @RequestMapping("/toTrade")
    @LoginRequired(loginSuccess = true)
    public String toTrade(HttpServletRequest request, HttpServletResponse response, Model model) {
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        // 收件人地址列表
        List<UmsMemberReceiveAddress> receiveAddressByMemberId = umsMemberService.getReceiveAddressByMemberId(memberId);
        // 将购物车集合转化为页面计算清单集合
        List<OmsCartItem> omsCartItems = cartService.cartList(memberId);
        List<OmsOrderItem> omsOrderItems = new ArrayList<>();
        for (OmsCartItem omsCartItem : omsCartItems) {
            if (omsCartItem.getIschecked() != null) {
                // 每循环一个购物车对象，就封装一个商品的详情到OmsOrderItem
                if (omsCartItem.getIschecked().equals("1")) {
                    OmsOrderItem omsOrderItem = new OmsOrderItem();
                    omsOrderItem.setProductName(omsCartItem.getProductName());
                    omsOrderItem.setProductPic(omsCartItem.getProductPic());
                    omsOrderItems.add(omsOrderItem);
                }
            }
        }
        model.addAttribute("omsOrderItems", omsOrderItems);
        model.addAttribute("userAddressList", receiveAddressByMemberId);
        model.addAttribute("nickName", nickname);
        // 被勾选商品的总额
        model.addAttribute("totalAmount", getTotalAmount(omsCartItems));
        model.addAttribute("tradeCode", "0");
        return "trade";
    }

    private BigDecimal getTotalAmount(List<OmsCartItem> omsCartItems) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OmsCartItem omsCartItem : omsCartItems) {
            BigDecimal totalPrice = omsCartItem.getTotalPrice();
            if (omsCartItem.getIschecked() != null) {
                totalAmount = totalAmount.add(totalPrice);
            }
        }
        return totalAmount;
    }
}
