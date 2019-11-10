package com.chirping.gmall.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.chirping.gmall.annotations.LoginRequired;
import com.chirping.gmall.payBean.PaymentInfo;
import com.chirping.gmall.payment.config.AlipayConfig;
import com.chirping.gmall.pojo.OmsOrder;
import com.chirping.gmall.service.OrderService;
import com.chirping.gmall.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 15211
 */
@Controller
public class AlipayController {

    @Resource
    PaymentService paymentService;
    @Resource
    AlipayClient alipayClient;
    @Reference
    OrderService orderService;

    @RequestMapping("/index")
    @LoginRequired(loginSuccess = true)
    public String index(String omsOrderId, String outTradeNo, BigDecimal totalAmount, HttpServletRequest request, Model model) {
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        model.addAttribute("nickName", nickname);
        model.addAttribute("orderId", outTradeNo);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("omsOrderId", omsOrderId);
        return "index";
    }

    @RequestMapping("/alipay/submit")
    @LoginRequired(loginSuccess = true)
    public void alipay(String omsOrderId, String orderId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String memberId = (String) request.getAttribute("memberId");
        OmsOrder order = orderService.getOmsOrderItemByIdOrderId(omsOrderId, memberId, orderId);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderSn();
        //付款金额，必填
        String total_amount = order.getTotalAmount().toString();
        //订单名称，必填
        String subject = "111";
        //商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //    + "\"total_amount\":\""+ total_amount +"\","
        //    + "\"subject\":\""+ subject +"\","
        //    + "\"body\":\""+ body +"\","
        //    + "\"timeout_express\":\"10m\","
        //    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
        String head = "<html><head><meta http-equiv='Content-Type' content='text/html;charset=utf-8'></head>";
        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        String buttom = "<body></body></html>";
        // 生成并且保存用户的支付信息
        OmsOrder omsOrder = orderService.getOrderByOutTradeNo(orderId);
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(omsOrder.getId());
        paymentInfo.setOrderSn(orderId);
        paymentInfo.setPaymentStatus("未付款");
        paymentInfo.setSubject("谷粒商城商品一件");
        paymentInfo.setTotalAmount(order.getTotalAmount());
        paymentService.savePaymentInfo(paymentInfo);
        response.getWriter().println(head + result + buttom);
    }

    @RequestMapping("/mx/submit")
    @LoginRequired(loginSuccess = true)
    public void wxapy(String omsOrderId, String orderId, HttpServletRequest request, HttpServletResponse response) {

    }
}