package com.itheima.travel.web.servlet;

import cn.hutool.core.util.StrUtil;
import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RouteServlet")
public class RouteServlet extends BaseServlet {

    /**
     * 获取service对象
     */
   RouteService routeService = (RouteService) BeanFactory.getBean("routeService");

    /**
     * 模板项
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void xxx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * 模板项
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收请求参数
        Integer rid = Integer.parseInt(request.getParameter("rid"));

        // 调用service
        Route route = routeService.findDetail(rid);

        // 写入request域中
        request.setAttribute("route",route);
        //转发视图
        request.getRequestDispatcher("/route_detail.jsp").forward(request,response);
    }


    /**
     * 点击浏览和搜索功能的分页
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收请求参数
        Integer cid = null;
        if (StrUtil.isNotEmpty(request.getParameter("cid"))) {
            cid = Integer.parseInt(request.getParameter("cid"));
        }
        Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));

        // 搜索传入的值
        String rname = request.getParameter("rname");

        // 调用service
        PageBean<Route> pb = routeService.findByPage(cid,pageNum,pageSize,rname);

        // 将PageBean 和 cid 写入到 request域中
        request.setAttribute("cid",cid);
        request.setAttribute("pb",pb);
        request.setAttribute("rname",rname);

        // 转发到route_list.jsp
        request.getRequestDispatcher("/route_list.jsp").forward(request,response);

    }
}
