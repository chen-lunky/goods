package com.chen.controller.user;

import com.chen.service.UserService;
import com.chen.utils.UserException;
import com.chen.utils.VerifyCodeUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.chen.domain.User;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户模块的表示层
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 换验证码
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/checkCode.do")
    public void checkCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        VerifyCodeUtils vc = new VerifyCodeUtils();
        BufferedImage image = vc.getImage();//获取一次性验证码图片
        // 该方法必须在getImage()方法之后来调用
       //System.out.println(vc.getText());//获取图片上的文本
        //把图片写到指定流中
        VerifyCodeUtils.output(image,response.getOutputStream());
        // 把文本保存到session中，为LoginServlet验证做准备
        request.getSession().setAttribute("verifyCode",vc.getText() );
    }

    /**
     * 校验用户名是否被注册
     * @param username

     * @return
     * @throws IOException
     */
    @RequestMapping("/validateUsername")
    public @ResponseBody boolean validateUsername(@RequestParam("username") String username) throws IOException {
        System.out.println("username = "+username);
        User user = userService.findByUsername(username);
        boolean b = false;
        if (user ==null){
            b=true;
        }
        //发给客户端
        //response.getWriter().print(b);
        return b;
    }

    /**
     * 校验邮箱是否被注册
     * @param email

     * @return
     * @throws IOException
     */
    @RequestMapping("/validateEmail")
    public @ResponseBody boolean validateEmail(@RequestParam("email") String email) throws IOException {
        User user = userService.findByEmail(email);
        System.out.println("email = "+email);

        boolean b = false;
        if (user ==null){
            b=true;
        }
        //response.getWriter().print(b);
        return b;
    }

    /**
     * 校验验证码是否正确
     * @param verifyCode
     * @param session
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping("/validateVerifyCode")
    public @ResponseBody boolean validateVerifyCode(@RequestParam("verifyCode") String verifyCode,HttpSession session) throws IOException {
        System.out.println("verifyCode = "+verifyCode);
        //获取图形上真正的验证码
        String verifyCode1 = (String)session.getAttribute("verifyCode");
        boolean b = false;
        //比较是否一致
        if (verifyCode.equalsIgnoreCase(verifyCode1)){
            b=true;
        }
        return b;
    }

    /**
     * 注册功能
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/regist.do")
    public ModelAndView regist(User user,HttpSession session) throws IOException {
        ModelAndView mv = new ModelAndView();
        //校验注册，失败，保存错误信息，返回regist.jsp显示
        Map<String, String> errors = validateRegist(user,session);
        if (errors.size()>0){
            mv.addObject("errors",errors);
            mv.addObject("user",user);//回显数据
            mv.setViewName("forward:/pages/user/user/regist.jsp");
            return mv;
        }
        //保存信息
        userService.regist(user);
        mv.addObject("code","success");
        mv.addObject("msg","注册完成，请马上到邮箱完成激活");
        mv.setViewName("forward:/pages/user/msg.jsp");
        return mv;
    }

    /**
     * 激活功能
     */
    @RequestMapping("/activationCode.do")
    public ModelAndView activationCode(@RequestParam("uid") String uid,HttpServletResponse response) throws IOException {
        ModelAndView mv =new ModelAndView();
        //response.setContentType("text/html;charset=utf-8;");

        try {
            userService.activationCode(uid);
            mv.addObject("code","success");
            mv.addObject("msg","激活成功，请前往去登录");
        } catch (UserException e) {
            mv.addObject("code","error");
            mv.addObject("msg",e.getMessage());
        }
        mv.setViewName("forward:/pages/user/msg.jsp");
        return mv;

//        response.getWriter().println("激活成功");
//        response.getWriter().println("<html>");
//        response.getWriter().println("<head>");
//        response.getWriter().println("</head>");
//        response.getWriter().println("<a href='http://localhost:8080/goods/pages/user/user/login.jsp'>请去登录</a>");
//        response.getWriter().println("</body>");
//        response.getWriter().println("</html>");


    }

    /**
     * 注册校验
     * @param user

     * @return
     */
    private Map<String,String> validateRegist(User user,HttpSession session) throws IOException {
        Map<String,String> errors = new HashMap<String,String>();
        //校验用户名
        String username = user.getUsername();
        if (username == null || username.trim().isEmpty()){
            errors.put("username","用户名不能为空");
        }else if (!this.validateUsername(username)){
            errors.put("username","该用户名已被注册");
        }
        //校验密码
        String password = user.getPassword();
        if (password == null || password.trim().isEmpty()){
            errors.put("password","密码名不能为空");
        }else if (password.length()<3 || password.length()>20){
            errors.put("password","密码的长度要3-20之间");
        }
        //校验密码
        String confirmPassword = user.getConfirmPassword();
        if (confirmPassword == null || confirmPassword.trim().isEmpty()){
            errors.put("confirmPassword","确认密码名不能为空");
        }else if (!password.equals(confirmPassword)){
            errors.put("confirmPassword","两次输入不一致");
        }
        //校验email
        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()){
            errors.put("username","邮箱不能为空");
        }else if (!this.validateUsername(username)){
            errors.put("username","该邮箱已被注册");
        }else if (!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
            errors.put("username","邮箱的格式有问题");
        }
        //校验验证码
        String verifyCode = user.getVerifyCode();
        if (verifyCode == null || verifyCode.trim().isEmpty()){
            errors.put("verifyCode","验证码不能为空");
        }else if (!this.validateVerifyCode(verifyCode,session)){
            errors.put("verifyCode","验证码错误");
        }
        return errors;
    }

    /**
     * 登录功能
     * @return
     */
    @RequestMapping("/login.do")
    public ModelAndView login(User fromUser,HttpSession session,HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();

        //调用service方法
        User user = userService.login(fromUser);
        //判断用户是否空
        if (user ==null){//用户为空
            mv.addObject("msg","该用户不存在");
            mv.setViewName("forward:/pages/user/user/login.jsp");
            return mv;
        }else {
            if (!fromUser.getPassword().equals(user.getPassword())){
                mv.addObject("msg","密码错误");
                mv.addObject("user",fromUser);//回显数据
                mv.setViewName("forward:/pages/user/user/login.jsp");
            }else {
                if (user.getStatus()==0){
                    mv.addObject("msg","您尚未激活，请前往邮箱激活");
                    mv.addObject("user",fromUser);//回显数据
                    mv.setViewName("forward:/pages/user/user/login.jsp");
                }else if (user.getStatus()==1){//登录成功
                    //把当前用户存入session中
                    session.setAttribute("user",user);
                    //把用户名保存cookie中
                    //解决中文用户名保存在cookie中的乱码问题
                    String username = URLEncoder.encode(user.getUsername(),"utf-8");
                    Cookie cookie = new Cookie("username",username);
                    cookie.setMaxAge(60*60*24*10);//保存10天
                    response.addCookie(cookie);
                    mv.setViewName("redirect:/index.jsp");
                }
            }

            return mv;
        }
    }
    /**
     * 修改密码
     * @return
     */
    @RequestMapping("/updatePassword.do")
    public ModelAndView updatePassword(User user,@RequestParam(value = "newPassword")String newPassword,HttpSession session){
        ModelAndView mv = new ModelAndView();
        User sessionUser =(User) session.getAttribute("user");
        if (sessionUser ==null){
            mv.addObject("msg","你还没有登录，不能修改密码");
            mv.setViewName("forward:/pages/user/user/login.jsp");
            return mv;
        }
        try {
            userService.updatePassword(sessionUser.getUid(),user.getPassword(),newPassword);
            mv.addObject("msg","修改密码成功，请重新登录");
            mv.setViewName("forward:/pages/user/user/login.jsp");
        } catch (UserException e) {
             mv.addObject("msg",e.getMessage());
             //回显数据
             mv.addObject("user",user);
             mv.addObject("newPassword",newPassword);
             mv.setViewName("forward:/pages/user/user/pwd.jsp");
        }
        return mv;
    }
    @RequestMapping("/logout.do")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/pages/user/user/login.jsp";
    }
}
