package com.chen.filter;

import com.chen.domain.Admin;
import com.chen.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = {"/pages/admin/book/*","/adminBook/*","/pages/admin/category/*","/adminCategory/*",
        "/pages/admin/order/*","/adminOrder/*"})
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin ==null){
            request.setAttribute("msg","您还没有登录，不能访问！！,请登录");
            request.getRequestDispatcher("/pages/admin/login.jsp").forward(req,resp);
        }else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
