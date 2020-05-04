package com.chen.service.impl;

import com.chen.dao.UserDao;
import com.chen.domain.User;
import com.chen.service.UserService;
import com.chen.utils.*;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * 用户模块的逻辑层接口实现类
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     *  校验用户名是否被注册
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 校验邮箱是否被注册
     * @param email
     * @return
     */
    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * 注册功能
     * @param user
     */
    @Override
    public void regist(User user){
        //设置用户的id
        user.setUid(CommonUtils.uuid());
        //设置用户的状态
        user.setStatus(0);
        //设置用户的激活码
        user.setActivationCode(CommonUtils.uuid()+CommonUtils.uuid());
        //添加一个用户
        userDao.add(user);
        //发邮箱

//        Properties prop = new Properties();
//        try {
//            prop.load(this.getClass().getClassLoader().getResourceAsStream("email.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //登录邮件服务器，得到session
//        String host = prop.getProperty("host");//服务器主机名
//        String username = prop.getProperty("username");//登录名
//        String password = prop.getProperty("password");//登录密码
//        Session  session = MailUtils.createSession(host,username,password);
//        //创建Mail对象
//        String from = prop.getProperty("from");//发件人
//        String to=user.getEmail();//收件人
//        String subject = prop.getProperty("subject");//主题
//        /**
//         * MessageFormat.format()方法的第一个参数{0}，使用第二个参数代替
//         * 比如，MessageFormat.format("你好{0}，{1}"，"张三","我是。。")；
//         * 返回你好张三，我是。。
//         */
//        String content = MessageFormat.format(prop.getProperty("content"),user.getUid());//正文
//        Mail mail = new Mail(from,to,subject,content);
//        try {
//            MailUtils.send(session,mail);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        String context = "恭喜您已经完成注册，请点击<a href = 'http://localhost:8080/goods/user/activationCode.do?uid="+user.getUid()+"'>激活【网上书城】</a>";
        System.out.println("uid="+user.getUid());
        System.out.println("context = " +context);
        Mail1Utils.sendMail(user.getEmail(),context,"激活邮件");


    }

    /**
     * 激活功能
     * @param uid
     */
    @Override
    public void activationCode(String uid) throws UserException {
        //根据用户uid查询用户
        User user = userDao.findByUid(uid);
        //判断user是否为null
        if (user == null){//user为null,抛出无效的激活码
            throw new UserException("无效的激活码！！");
        }else {
            //如果用户的状态为1.抛出用户您已激活，请勿二次激活
            if (user.getStatus() == 1){
                throw new UserException("您已激活，请勿二次激活！！");
            }else if (user.getStatus() == 0){
                userDao.updateStatus(uid);
            }
        }

    }

    @Override
    public User login(User fromUser) {
        return userDao.findByUsername(fromUser.getUsername());
    }

    /**
     * 修改密码
     * @param uid
     * @param oldPassword
     * @param newPassword
     * @throws UserException
     */
    @Override
    public void updatePassword(String uid,String oldPassword,String newPassword) throws UserException {
        //根据uid和旧密码查询用户是否存在
        User user1= userDao.findByUidAndPassword(uid,oldPassword);
        if (user1 == null){
            throw new UserException("旧密码错误");
        }
        userDao.updatePassword(uid,newPassword);
    }
}
