package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.Cart;
import com.itheima.travel.domain.CartItem;
import com.itheima.travel.domain.Route;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.util.BeanFactory;
import com.itheima.travel.util.CartUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {
    RouteService routeService = (RouteService) BeanFactory.getBean("routeService");

    protected void xxx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * 显示购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从session中获取对象
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        // 从redis中获取购物车对象
        Cart cart = CartUtils.redis2Cart(currentUser.getUid());
        // 将购物车写入request域
        request.setAttribute("cart",cart);
        // 转发到购物车
        request.getRequestDispatcher("/cart.jsp").forward(request,response);
    }

    /**
     * 删除购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void delCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收请求参数
        String rid = request.getParameter("rid");

        // 2.从session中获取user
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        // 3.从redis中获取cart
        Cart cart = CartUtils.redis2Cart(currentUser.getUid());

        // 4.删除购物项
        cart.getCartItemMap().remove(rid);

        // 5.将cart重新写入redis中
        CartUtils.cart2Redis(currentUser.getUid(), cart);

        // 6.重定向到购物车查看页面
        response.sendRedirect(request.getContextPath()+"/CartServlet?action=showCart");
    }

    /**
     * 添加购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收前台参数
        String rid = request.getParameter("rid");
        Integer num = Integer.parseInt(request.getParameter("num"));

        // 从redis中获取购物车
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        Cart cart = CartUtils.redis2Cart(currentUser.getUid());

        // 获取购物项集合
        LinkedHashMap<String, CartItem> cartItemMap = cart.getCartItemMap();

        // 判断此商品是否在购物项集合中
        CartItem cartItem = cartItemMap.get(rid);
        if (cartItem != null){
            // 数量增加
            cartItem.setNum(cartItem.getNum()+num);
        }else {
            // 创建新的购物项
            cartItem = new CartItem();
            cartItem.setNum(num);
            Route route = routeService.findDetail(Integer.parseInt(rid));
            cartItem.setRoute(route);
            // 设置到购物项集合中
            cartItemMap.put(rid,cartItem);
        }
        // 将最新购物车保存到redis中
        CartUtils.cart2Redis(currentUser.getUid(),cart);
        System.out.println(cartItem);
        // 将cartItem写入request域
        request.setAttribute("cartItem",cartItem);

        // 转发到添加成功页面
        request.getRequestDispatcher("/cart_success.jsp").forward(request,response);
    }


}