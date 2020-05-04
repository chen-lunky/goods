package com.chen.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * 本类只有这么一个方法，用来发邮件！
 */
public class MailUtils {
    public static Session createSession(String host,final String username,final String password){
        Properties prop = new Properties();
        //指定主机
        prop.setProperty("mail.host",host);
        //指定验证为true
        prop.setProperty("mail.smtp.auth","true");
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };
        //获取session对象
        return Session.getInstance(prop,auth);
    }

    /**
     * 发送指定的邮件
     * @param session
     * @param mail
     * @throws MessagingException
     */
    public static void send(Session session,final  Mail mail) throws MessagingException, IOException {
        // 创建邮件对象
        MimeMessage msg = new MimeMessage(session);
        // 设置发件人
        msg.setFrom(new InternetAddress(mail.getFrom()));
        // 设置收件人
        msg.addRecipients(Message.RecipientType.TO,mail.getToAddress());

        //设置抄送
        String cc = mail.getCcAddress();
        if (!cc.isEmpty()){
            msg.addRecipients(Message.RecipientType.CC,cc);
        }
        //设置暗送
        String bcc = mail.getBccAddress();
        if (!bcc.isEmpty()){
            msg.addRecipients(Message.RecipientType.BCC,bcc);
        }
        //设置主题
        msg.setSubject(mail.getSubject());
        //创建部件集对象
        MimeMultipart parts = new MimeMultipart();
        //创建一个部件
        MimeBodyPart part = new MimeBodyPart();
        // 设置邮件文本内容
        part.setContent(mail.getContent(),"text/html;charset=utf-8");
        // 把部件添加到部件集中
        parts.addBodyPart(part);
        //添加附件
        //获取所有附件
        List<AttachBean> attachBeans = mail.getAttachs();
        if (attachBeans!=null){
            for (AttachBean attachBean : attachBeans) {
                // 创建一个部件
               MimeBodyPart attachPart =  new MimeBodyPart();
              //设置附件文件
               attachPart.attachFile(attachBean.getFile());
               //设置附件文件名
              attachPart.setFileName(MimeUtility.encodeText(attachBean.getFileName()));
              String cid = attachBean.getCid();
              if (cid !=null) {
                  attachPart.setContentID(cid);
              }
              parts.addBodyPart(attachPart);

            }
        }
        // 给邮件设置内容
        msg.setContent(parts);
        // 发邮件
        Transport.send(msg);
    }
}
