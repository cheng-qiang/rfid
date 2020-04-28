package org.chen.mailserver.receiver;

import org.chen.rfid.model.station.Visitors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月25日 9:14
 * @Description:
 */
@Component
public class MailReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @RabbitListener(queues = "chen.mail.visitors")
    public void handler(Visitors visitors){
        logger.info(visitors.toString()+"===>接收到了！");
        //收到消息，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            //发给访客
            helper.setTo(visitors.getEmail());
            //机房人员管理系统邮箱地址
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("机房访客");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name", visitors.getName());
            context.setVariable("phone", visitors.getPhone());
            context.setVariable("identity_number", visitors.getIdentityNumber());
            context.setVariable("company", visitors.getCompany());
            context.setVariable("department", visitors.getDepartment());
            context.setVariable("email", visitors.getEmail());
            context.setVariable("date", visitors.getDate());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("邮件发送失败："+e.getMessage());
        }

    }

}
