package com.itheima.travel.web.servlet;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.UserService;
import com.itheima.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {

    // 全局变量调用UserServiceImpl
    UserService userService = new UserServiceImpl();


    /**
     * @description 实现用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收请求参数
        String telephone = request.getParameter("telephone");
        String smsCode = request.getParameter("smsCode");
        // 2.获取session中验证码
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("session_code_" + telephone);
        // 3.判断
        // 验证码为空或不一致拦截
        if (sessionCode == null || !sessionCode.equals(smsCode)) {
            request.setAttribute("message", "验证码为空或不一致");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }


        //----------------------------------
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
        ResultInfo resultInfo = userService.register(user);
        // System.out.println(resultInfo);

        //转发视图
        if (resultInfo.getSuccess()){
            response.sendRedirect(request.getContextPath()+"/register_ok.jsp");
        }else {
            request.setAttribute("message",resultInfo.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request,response);
        }


    }

    /**
     * @description 验证用户名
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxCheackUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收请求参数
        String username = request.getParameter("username");
        // 调用service查询
        User user = userService.findByUsername(username);

        // 判断结果
        ResultInfo resultInfo = null;
        // 已存在的情况
        if (user!=null){

            resultInfo = new ResultInfo(false,"用户名已存在");
        }else {
            // 不存在的情况
            resultInfo = new ResultInfo(true,"√");

        }
        // 将结果转为json new ObjectMapper.writeValueAsString
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(resultInfo);


        // 响应到客户端
        // 声明格式
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);
    }

    /**
     * 手机号验证
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxCheackTelephone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收请求参数
        String telephone = request.getParameter("telephone");
        // 调用service查询
        User user = userService.findByTelephone(telephone);

        // 判断结果
        ResultInfo resultInfo = null;
        // 已存在的情况
        if (user!=null){

            resultInfo = new ResultInfo(false,"手机号已存在");
        }else {
            // 不存在的情况
            resultInfo = new ResultInfo(true,"√");

        }
        // 将结果转为json new ObjectMapper.writeValueAsString
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(resultInfo);


        // 响应到客户端
        // 声明格式
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);
    }

    /**
     * 发送验证码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxSendSms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收参数
        String telephone = request.getParameter("telephone");


        // 生成六位随机数
        String code = RandomUtil.randomNumbers(6);
        // 调用service发送短信
        ResultInfo resultInfo = userService.sendSms(telephone,code);
        // 判断结果
        if (resultInfo.getSuccess()){
            request.getSession().setAttribute("session_code_"+telephone,code);
        }
        // 转json
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(resultInfo);
        //响应到客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);
    }
    /*

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    */
}