package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.Address;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.AddressService;
import com.itheima.travel.util.BeanFactory;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/AddressServlet")
public class AddressServlet extends BaseServlet {
    AddressService addressService = (AddressService) BeanFactory.getBean("addressService");

    /**
     * 通过uid查找
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        String uid = request.getParameter("uid");
        // System.out.println(uid);
        // 根据uid查询service
        List<Address> list = addressService.findByUid(uid);

        // 将list存储到request域
        request.setAttribute("list",list);

        // 转发到 地址列表页面
        request.getRequestDispatcher("/home_address.jsp").forward(request, response);

    }

    /**
     * 保存地址
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Address address = new Address();
        try {
            BeanUtils.populate(address,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("封装失败");
        }
        User  currentUser =(User) request.getSession().getAttribute("currentUser");
        address.setUser(currentUser);
        address.setIsdefault("0");

        // 调用service保存
        addressService.save(address);

        // 重定向
        response.sendRedirect(request.getContextPath()+"/AddressServlet?action=findByUid&uid="+currentUser.getUid());

    }


    protected void updateByAid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String upcontact = request.getParameter("upcontact");
        int aid = Integer.parseInt(request.getParameter("aid"));
        String upaddress = request.getParameter("upaddress");
        String uptelephone = request.getParameter("uptelephone");
        Address address = new Address();
        address.setAid(aid);
        address.setContact(upcontact);
        address.setAddress(upaddress);
        address.setTelephone(uptelephone);
        // 修改
        addressService.updateByAid(address);

        User  currentUser =(User) request.getSession().getAttribute("currentUser");
        // 重定向
        response.sendRedirect(request.getContextPath()+"/AddressServlet?action=findByUid&uid="+currentUser.getUid());


    }


    protected void deleAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String aid = request.getParameter("aid1");

        addressService.deleAddress(aid);

        User  currentUser =(User) request.getSession().getAttribute("currentUser");
        // 重定向
        response.sendRedirect(request.getContextPath()+"/AddressServlet?action=findByUid&uid="+currentUser.getUid());

    }


    protected void setupthis(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String aid = request.getParameter("aid");
        User  currentUser =(User) request.getSession().getAttribute("currentUser");
        int uid = currentUser.getUid();
        addressService.setZero(uid);

        addressService.setOne(uid,aid);

        response.sendRedirect(request.getContextPath()+"/AddressServlet?action=findByUid&uid="+currentUser.getUid());

    }

    protected void xxx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}