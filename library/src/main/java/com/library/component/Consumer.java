package com.library.component;

import com.google.gson.Gson;
import com.library.pojo.User;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

//暂时不使用
@Component
public class Consumer {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String mailFrom;
//    @Value("${spring.mail.to}")
//    private String mailTo;
    @Value("${spring.mail.cc}")
    private String mailCc;
    //首页地址


    private String head="http://10.10.102.166:8081/";


    @RabbitListener(queuesToDeclare = @Queue("mail_queue"))
    public void myListener(String user) throws Exception {
        User userT=new Gson().fromJson(user, User.class);
        System.out.println("User:" +userT );


        if(userT.getEmail()!=null){
            //创建一个复杂的消息邮件
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            //邮件设置 helper.setSubject("邮件附件发送测试");
            String rep="<b style=\"color: red\">注册成功</b><br />\n" +
                    "    <a href='"+head+"'>请前往首页</a>";
            helper.setText(rep, true);
//            System.err.println(rep);
            helper.setTo(userT.getEmail());
            helper.setFrom(mailFrom);

            helper.setSubject("PY图书馆");
            //上传文件
            //
//        helper.addAttachment("1.jpg", new File("src/main/resources/uploadFile/1.jpg"));
//        helper.addAttachment("2.jpg", new File("src/main/resources/uploadFile/2.jpg"));
            mailSender.send(mimeMessage);
        }
    }

}
