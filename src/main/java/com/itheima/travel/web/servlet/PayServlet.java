package com.itheima.travel.web.servlet;

import com.itheima.travel.util.PayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/PayServlet")
public class PayServlet extends BaseServlet {


    /**
     * 生成支付链接
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void genPayUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收请求参数
        // 订单编号
        String oid = request.getParameter("oid");
        // 订单金额
        String total = request.getParameter("total");

        // 调用微信工具类
        String payUrl = PayUtils.createOrder(oid, 1);
        // 将数据写入request域中
        request.setAttribute("oid",oid);
        request.setAttribute("total",total);
        request.setAttribute("payUrl",payUrl);

        // 转发到支付页面
        request.getRequestDispatcher("/pay.jsp").forward(request,response);
    }

}