package com.itheima.travel.web.servlet;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.UserService;
import com.itheima.travel.util.BeanFactory;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Map;

@WebServlet("/UserServlet")
@MultipartConfig // 支持文件上传解析
public class UserServlet extends BaseServlet {

    // 全局变量调用UserServiceImpl

    UserService userService = (UserService) BeanFactory.getBean("userService");


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
        if (telephone==null){
            request.setAttribute("message", "手机号码不能为空");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        String username = request.getParameter("username");
        if (username==null){
            request.setAttribute("message", "用户名不能为空");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        String password = request.getParameter("password");
        if (password==null){
            request.setAttribute("message", "密码不能为空");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
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
        // System.out.println(username);
        // 判断结果
        ResultInfo resultInfo = null;
        if (username.equalsIgnoreCase("")){
            resultInfo = new ResultInfo(false,"用户名不能为空");
        }else {
            // 调用service查询
            User user = userService.findByUsername(username);
            // 已存在的情况
            if (user!=null){
                resultInfo = new ResultInfo(false,"用户名已存在");
            }else {
                // 不存在的情况
                resultInfo = new ResultInfo(true,"√");
            }
        }
        // 将结果转为json new ObjectMapper.writeValueAsString
        java2JsonWriteClient(resultInfo,response);
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
        java2JsonWriteClient(resultInfo,response);
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
        java2JsonWriteClient(resultInfo,response);

    }

    /**
     * 密码登陆
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxLoginPwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收参数2个
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 调用service
        ResultInfo resultInfo = userService.loginPwd(username,password);
        // 登陆成功,写入session
        if (resultInfo.getSuccess()) {
            request.getSession().setAttribute("currentUser", resultInfo.getData());
        }


        // 清除user信息(setDate null)
        resultInfo.setData(null);
        // 转json,响应到浏览器
        java2JsonWriteClient(resultInfo,response);


    }

    /**
     * 退出登录状态
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 清除用户信息
        request.getSession().removeAttribute("currentUser");
        // 重定向到前台
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }
    /*

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    */

    /**
     * 修改个人信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // String uid = request.getParameter("uid");
        // System.out.println(uid);
        // 获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        // 创建实体对象
        User user = new User();
        // 封装实体对象
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("实体封装失败");
        }

        // 头像上传---------------------------------------------------开始
        // 获取文件对象
        Part picPart = request.getPart("pic");
        // 获取文件名
        String fileName = picPart.getSubmittedFileName();
        // 判断存在
        if ((fileName.length()>0)) {
            // 用户提交头像
            // 生成随机文件名
            fileName = IdUtil.simpleUUID()+fileName;
            String realPath = request.getServletContext().getRealPath("/pic/" + fileName);
            picPart.write(realPath);
            // 写入实体
            user.setPic("pic/"+fileName);
        }


        // 头像上传---------------------------------------------------结束



        userService.updateInfo(user);
        // 查询结果
        User currentUser = userService.findByUid(user.getUid());

        request.getSession().setAttribute("currentUser",currentUser);

        // 重定向到页面
        response.sendRedirect(request.getContextPath()+"/home_index.jsp");

    }


    /**
     * 短信登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void telLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收请求参数
        String telephone = request.getParameter("telephone");
        String smsCode = request.getParameter("smsCode");
        // 根据手机号查询
        User byTelephone = userService.findByTelephone(telephone);
        ResultInfo resultInfo =null;
        if (byTelephone == null){
            resultInfo= new ResultInfo(false,"手机号不存在");

        }else{
            String attribute =(String) request.getSession().getAttribute("session_code_" + telephone);
            if (!attribute.equals(smsCode)){
                resultInfo = new ResultInfo(false,"验证码错误");
            }else {
                request.getSession().setAttribute("currentUser",byTelephone);
                resultInfo = new ResultInfo(true);
            }
        }
        java2JsonWriteClient(resultInfo,response);
    }



}