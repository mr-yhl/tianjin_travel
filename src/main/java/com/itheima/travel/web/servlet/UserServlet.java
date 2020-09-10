package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    // 全局变量调用UserServiceImpl
    UserServiceImpl userServiceImpl = new UserServiceImpl();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取前台参数
        String action = request.getParameter("action");
        // 进行方法的判断调用
        if ("register".equals(action)){
            this.register(request,response);
        }
    }

    /**
     * @description 实现用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取前台参数
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        // 封装实体
        User user = new User();
        try {
            BeanUtils.populate(user,requestParameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new  RuntimeException("实体封装失败..");
        }

        // 调用service
        ResultInfo resultInfo = userServiceImpl.register(user);
        // System.out.println(resultInfo);

        //转发视图
        if (resultInfo.getSuccess()){
            response.sendRedirect(request.getContextPath()+"/register_ok.jsp");
        }else {
            request.setAttribute("message",resultInfo.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request,response);
        }


    }
    /*

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    */
}