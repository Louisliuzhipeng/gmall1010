package com.chirping.gmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chirping.gmall.annotations.LoginRequired;
import com.chirping.gmall.pojo.OmsCartItem;
import com.chirping.gmall.pojo.OmsOrder;
import com.chirping.gmall.pojo.OmsOrderItem;
import com.chirping.gmall.pojo.UmsMemberReceiveAddress;
import com.chirping.gmall.service.CartService;
import com.chirping.gmall.service.OrderService;
import com.chirping.gmall.service.SpuService;
import com.chirping.gmall.service.UmsMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @Reference
    OrderService orderService;
    @Reference
    SpuService spuService;

    @RequestMapping("/submitOrder")
    @LoginRequired(loginSuccess = true)
    public ModelAndView submitOrder(String receiveAddressId, BigDecimal totalAmount, String tradeCode, HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap modelMap) {
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        // 检查交易码
        String success = orderService.checkTradeCode(memberId, tradeCode);
        if (success.equals("success")) {
            List<OmsOrderItem> omsOrderItems = new ArrayList<>();
            // 订单对象
            OmsOrder omsOrder = new OmsOrder();
            omsOrder.setAutoConfirmDay(7);
            omsOrder.setCreateTime(new Date());
            omsOrder.setDiscountAmount(null);
            //omsOrder.setFreightAmount(); 运费，支付后，在生成物流信息时
            omsOrder.setMemberId(memberId);
            omsOrder.setMemberUsername(nickname);
            omsOrder.setNote("快点发货");
            String outTradeNo = "gmall";
            outTradeNo = outTradeNo + System.currentTimeMillis();// 将毫秒时间戳拼接到外部订单号
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmss");
            outTradeNo = outTradeNo + sdf.format(new Date());// 将时间字符串拼接到外部订单号
            omsOrder.setOrderSn(outTradeNo);//外部订单号
            omsOrder.setPayAmount(totalAmount);
            omsOrder.setOrderType(1);
            UmsMemberReceiveAddress umsMemberReceiveAddress = umsMemberService.getReceiveAddressById(receiveAddressId);
            omsOrder.setReceiverCity(umsMemberReceiveAddress.getCity());
            omsOrder.setReceiverDetailAddress(umsMemberReceiveAddress.getDetailAddress());
            omsOrder.setReceiverName(umsMemberReceiveAddress.getName());
            omsOrder.setReceiverPhone(umsMemberReceiveAddress.getPhoneNumber());
            omsOrder.setReceiverPostCode(umsMemberReceiveAddress.getPostCode());
            omsOrder.setReceiverProvince(umsMemberReceiveAddress.getProvince());
            omsOrder.setReceiverRegion(umsMemberReceiveAddress.getRegion());
            omsOrder.setPayType(0);
            // 当前日期加一天，一天后配送
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 1);
            Date time = c.getTime();
            omsOrder.setReceiveTime(time);
            omsOrder.setSourceType(0);
            omsOrder.setStatus(0);
            omsOrder.setOrderType(0);
            omsOrder.setTotalAmount(totalAmount);
            // 根据用户id获得要购买的商品列表(购物车)，和总价格
            List<OmsCartItem> omsCartItems = cartService.cartList(memberId);
            for (OmsCartItem omsCartItem : omsCartItems) {
                if (omsCartItem.getIschecked().equals("1")) {
                    // 获得订单详情列表
                    OmsOrderItem omsOrderItem = new OmsOrderItem();
                    // 检价
                    boolean b = spuService.checkPrice(omsCartItem.getProductSkuId(), omsCartItem.getPrice());
                    if (b == false) {
                        ModelAndView mv = new ModelAndView("tradeFail");
                        return mv;
                    }
                    // 验库存,远程调用库存系统
                    omsOrderItem.setProductPic(omsCartItem.getProductPic());
                    omsOrderItem.setProductName(omsCartItem.getProductName());
                    omsOrderItem.setOrderSn(outTradeNo);// 外部订单号，用来和其他系统进行交互，防止重复
                    omsOrderItem.setProductCategoryId(omsCartItem.getProductCategoryId());
                    omsOrderItem.setProductPrice(omsCartItem.getPrice());
                    omsOrderItem.setRealAmount(omsCartItem.getTotalPrice());
                    omsOrderItem.setProductQuantity(omsCartItem.getQuantity());
                    omsOrderItem.setProductSkuCode("111111111111");
                    omsOrderItem.setProductSkuId(omsCartItem.getProductSkuId());
                    omsOrderItem.setProductId(omsCartItem.getProductId());
                    omsOrderItem.setProductSn("仓库对应的商品编号");// 在仓库中的skuId
                    omsOrderItems.add(omsOrderItem);
                }
            }
            omsOrder.setOmsOrderItems(omsOrderItems);
            // 将订单和订单详情写入数据库
            // 删除购物车的对应商品
            omsOrder = orderService.saveOrder(omsOrder);
            // 重定向到支付系统
            ModelAndView mv = new ModelAndView("redirect:http://payment.gmall.com/index");
            mv.addObject("omsOrderId", omsOrder.getId());
            mv.addObject("outTradeNo", outTradeNo);
            mv.addObject("totalAmount", totalAmount);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("tradeFail");
            return mv;
        }
    }

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
        // 生成交易码，为了在提交订单时做交易码的校验
        String tradeCode = orderService.genTradeCode(memberId);
        model.addAttribute("tradeCode", tradeCode);
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
