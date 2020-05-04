package com.chen.filter;

import com.chen.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginUserFilter",urlPatterns = {"/pages/user/cart/*","/cart/*","/pages/user/order/*","/order/*"})
public class LoginUserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        User user = (User)request.getSession().getAttribute("user");
        if (user ==null){
            request.setAttribute("code","error");
            request.setAttribute("msg","您还没有登录，不能访问！！");
            request.getRequestDispatcher("/pages/user/msg.jsp").forward(req,resp);
        }else {
            chain.doFilter(req, resp);
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
