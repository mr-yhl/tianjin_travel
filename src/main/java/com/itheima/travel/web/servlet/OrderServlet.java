package com.itheima.travel.web.servlet;

import cn.hutool.core.util.IdUtil;
import com.itheima.travel.domain.*;
import com.itheima.travel.service.AddressService;
import com.itheima.travel.service.OrderService;
import com.itheima.travel.util.BeanFactory;
import com.itheima.travel.util.CartUtils;
import com.itheima.travel.util.JedisUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 订单的servlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {
    /**
     * 通过工程类引入AddressService
     */
    AddressService addressService = (AddressService) BeanFactory.getBean("addressService");
    OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
    /**
     * 模板
     */
    protected void xxx(HttpServletRequest requsest, HttpServletResponse response) throws ServletException, IOException {

    }
    /**
     * 判断是否支付
     */
    protected void isPay(HttpServletRequest requsest, HttpServletResponse response) throws ServletException, IOException {
        // 接收请求参数
        String oid = requsest.getParameter("oid");
        System.out.println(oid);

        // 调用service查询订单状态
        ResultInfo resultInfo = orderService.isPay(oid);
        // System.out.println("servlet"+resultInfo.getSuccess());
        // 转为json,响应到客户端
        java2JsonWriteClient(resultInfo,response);
    }

    /**
     * 提交订单
     * @throws ServletException
     * @throws IOException
     */
    protected void addOrder(HttpServletRequest requsest, HttpServletResponse response) throws ServletException, IOException {
        // 1. 接收请求参数 addressid 地址信息 姓名 地址 联系电话
        String addressId = requsest.getParameter("addressId");
        String[] split = addressId.split(",");
        // 2. 从redis中获取购物车
        User currentUser = (User) requsest.getSession().getAttribute("currentUser");
        Cart cart = CartUtils.redis2Cart(currentUser.getUid());
        // 3. 封装订单
        Order order = new Order();
        // 订单编号
        order.setOid(IdUtil.simpleUUID());
        // 下单时间
        order.setOrdertime(new Date());
        // 订单金额
        order.setTotal(cart.getTotalPrice());
        // 支付状态
        order.setState(0);
        // 订单地址姓名
        order.setContact(split[0]);
        order.setAddress(split[0]);
        order.setTelephone(split[0]);

        // 4. 封装订单项
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        LinkedHashMap<String, CartItem> cartItemMap = cart.getCartItemMap();
        Set<String> cartItemSet = cartItemMap.keySet();
        for (String key : cartItemSet) {
            OrderItem orderItem = new OrderItem();
            // 下单时间
            orderItem.setItemtime(new Date());
            // 支付状态
            orderItem.setState(0);
            // 数量
            orderItem.setNum(cartItemMap.get(key).getNum());
            // 小计
            orderItem.setSubtotal(cartItemMap.get(key).getSubTotal());
            // 商品id
            orderItem.setRid(Integer.valueOf(key));
            // 订单id
            orderItem.setOid(order.getOid());

            orderItems.add(orderItem);
        }
        order.setOrderItemList(orderItems);

        // 5. 调用service保存订单和订单项
        orderService.save(order);
        // 6. 清空购物车
        Jedis jedis = JedisUtils.getJedis();
        jedis.del("travel_cart_" + currentUser.getUid());
        jedis.close();
        // 7. 重定向到支付页面
        response.sendRedirect(requsest.getContextPath()+"/PayServlet?action=genPayUrl&oid=" + order.getOid() + "&total=" + order.getTotal());
    }

    /**
     * 商品结算
     */
    protected void orderInfo(HttpServletRequest requsest, HttpServletResponse response) throws ServletException, IOException {
        // 获取session中的用户对象
        User currentUser = (User) requsest.getSession().getAttribute("currentUser");
        // 查询收货地址
        List<Address> addressList = addressService.findByUid(String.valueOf(currentUser.getUid()));
        // 从redis中获取购物车
        Cart cart = CartUtils.redis2Cart(currentUser.getUid());
        // 将数据写入到request中
        requsest.setAttribute("addressList",addressList);
        requsest.setAttribute("cart",cart);
        // 转发视图
        requsest.getRequestDispatcher("/order_info.jsp").forward(requsest,response);

    }
}
