package com.chen.controller.admin;

import com.chen.domain.Admin;
import com.chen.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 管理员模块的Controller表示层
 */
@Controller
@RequestMapping("/admin")
public class AmdinController {
    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     * @param admin
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("/login.do")
    public ModelAndView login(Admin admin, HttpSession session,HttpServletResponse response) throws UnsupportedEncodingException {
        ModelAndView mv = new ModelAndView();
        Admin _admin = adminService.login(admin.getA_name(),admin.getA_password());
        if (_admin ==null){
            mv.addObject("msg","用户名或密码错误");
            mv.addObject("admin",admin);//回显数据
            mv.setViewName("forward:/pages/admin/login.jsp");
            return mv;
        }
        session.setAttribute("admin",_admin);
        //把用户名保存cookie中
        //解决中文用户名保存在cookie中的乱码问题
        String a_name = URLEncoder.encode(_admin.getA_name(),"utf-8");
        Cookie cookie = new Cookie("a_name",a_name);
        cookie.setMaxAge(60*60*24*10);//保存10天
        response.addCookie(cookie);
        mv.setViewName("redirect:/pages/admin/index.jsp");
        return mv;
    }

    /**
     * 管理员退出
     * @param session
     * @return
     */
    @RequestMapping("/logout.do")
    public String logout(HttpSession session){
        session.removeAttribute("admin");
        session.invalidate();
        return "redirect:/pages/admin/login.jsp";
    }

}
