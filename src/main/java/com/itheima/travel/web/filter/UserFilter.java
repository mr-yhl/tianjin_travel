package com.itheima.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/home_index.jsp","/home_left.jsp","/AddressServlet","/CartServlet"})
public class UserFilter implements Filter {
    /**
     *
     */
    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    /**
     *
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        // 向下转型
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object currentUser = request.getSession().getAttribute("currentUser");
        if (currentUser == null){
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }

        // 放行
        filterChain.doFilter(request, response);
    }

    /**
     *
     */
    @Override
    public void destroy() {
    }

}
