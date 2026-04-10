package com.bird_forum.util;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailUtils {

    // 发送邮件服务器配置 (以 QQ 邮箱为例)
    private static final String SMTP_HOST = "smtp.qq.com";
    private static final String SMTP_PORT = "465"; // SSL 加密端口
    private static final String USERNAME = "3614354287@qq.com";
    private static final String AUTH_CODE = "hmgsjditpvhkdbdh"; // 注意：不是登录密码

    /**
     * 获取邮件会话 Session
     */
    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        // 启用 SSL 加密
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        // --- 核心修复：强制使用 TLSv1.2 协议 ---
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // 创建会话，传入认证信息
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, AUTH_CODE);
            }
        });
    }

    /**
     * 发送简单文本邮件
     */
    public static void sendSimpleText(String to, String subject, String content) throws MessagingException {
        Session session = createSession();
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(content); // 纯文本内容

        Transport.send(message);
        System.out.println("文本邮件发送成功！");
    }

    /**
     * 发送 HTML 邮件
     */
    public static void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        Session session = createSession();
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        // 设置内容为 HTML，并指定编码防止乱码
        message.setContent(htmlContent, "text/html;charset=UTF-8");

        Transport.send(message);
        System.out.println("HTML邮件发送成功！");
    }

    /**
     * 发送带附件的邮件
     */
    public static void sendAttachmentEmail(String to, String subject, String content, String filePath) throws Exception {
        Session session = createSession();
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);

        // 1. 创建第一部分：邮件正文
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(content, "text/html;charset=UTF-8");

        // 2. 创建第二部分：附件
        MimeBodyPart attachmentPart = new MimeBodyPart();
        FileDataSource source = new FileDataSource(filePath);
        attachmentPart.setDataHandler(new DataHandler(source));
        // 设置附件文件名（防止中文乱码）
        attachmentPart.setFileName(source.getName());

        // 3. 将正文和附件组装到 Multipart 中
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(attachmentPart);

        // 4. 设置邮件的完整内容
        message.setContent(multipart);

        Transport.send(message);
        System.out.println("带附件邮件发送成功！");
    }
}