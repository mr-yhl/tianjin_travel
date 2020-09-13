package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求参数
        String action = req.getParameter("action");

        try {
            // 获取字节码对象
            Class clazz = this.getClass();
            // 根据action获取方法
            Method declaredMethod = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            // 执行对象
            declaredMethod.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("没有找到对应的方法");
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("服务器忙...");
        }

    }

    /**
     * 将对象转为json响应到客户端
      */
    public void java2JsonWriteClient(Object data, HttpServletResponse response) throws
            ServletException, IOException {
        // 将任意对象转为json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(data);
        // 响应到客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
}