package com.tmdrk.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TmdrkSpringbootTaskApplicationTests {
    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Test
    public void contextLoads01() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("通知-今晚开会");
        simpleMailMessage.setText("今晚七点，人民大会堂，开会，不要迟到！");
        simpleMailMessage.setTo("zhoujietmdrk@163.com");
        simpleMailMessage.setFrom("1308398245@qq.com");
        javaMailSender.send(simpleMailMessage);
        System.out.println("邮件发送完成.");
    }

    @Test
    public void contextLoads02() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //true 表示上传文件
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setSubject("通知-今晚开会"); //邮件标题
        mimeMessageHelper.setText("<b style='color:red'>今晚七点，人民大会堂，开会，不要迟到！</b>",true);//正文,文件
        mimeMessageHelper.setTo("zhoujietmdrk@163.com");
        mimeMessageHelper.setFrom("1308398245@qq.com");
        //上传文件
        mimeMessageHelper.addAttachment("1.jpg",new File("F:/图片/公章.jpg"));
        mimeMessageHelper.addAttachment("2.jpg",new File("F:/图片/主播现实.gif"));
        javaMailSender.send(mimeMessage);
        System.out.println("邮件发送完成.");
    }
}
