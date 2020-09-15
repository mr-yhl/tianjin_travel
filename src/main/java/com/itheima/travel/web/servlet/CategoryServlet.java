package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.Category;
import com.itheima.travel.service.CategoryService;
import com.itheima.travel.util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
    CategoryService categoryService = (CategoryService)BeanFactory.getBean("categoryService");

    /**
     * 导航分类查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxFindAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> list = categoryService.findAll();
        java2JsonWriteClient(list,response);
    }

    protected void xxx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }


}