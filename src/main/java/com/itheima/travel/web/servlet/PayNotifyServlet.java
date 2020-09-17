package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.itheima.travel.service.OrderService;
import com.itheima.travel.util.BeanFactory;
import com.itheima.travel.util.PayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 固定书写方式
 */
@WebServlet("/payNotify")
public class PayNotifyServlet extends HttpServlet {
    OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("接收到了");
        // 接收微信平台发送的请求
        XmlMapper xmlMapper = new XmlMapper();
        Map<String, String> map = xmlMapper.readValue(request.getInputStream(), Map.class);

        // 校验返回状态
        PayUtils.checkResultCode(map);

        orderService.updateState(map);
        System.out.println("修改了");

        // 返回微信支付结果通知
        Map<String, Object> result = new HashMap<>();
        result.put("return_code","SUCCESS");
        result.put("return_msg", "OK");
        String xml = xmlMapper.writeValueAsString(result);

        // 返回接收到通知
        response.sendRedirect("application/xml;charset=utf-8");
        response.getWriter().write(xml);

    }
}