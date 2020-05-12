package org.chen.mailserver.receiver;

import com.rabbitmq.client.Channel;
import org.chen.rfid.model.station.Visitors;
import org.chen.rfid.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
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

    @Autowired
    StringRedisTemplate redisTemplate;

    @RabbitListener(queues = Constants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel) throws IOException {
        Visitors visitors = (Visitors) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String) headers.get("spring_returned_message_correlation");
        if (redisTemplate.opsForHash().entries(Constants.MAIL_QUEUE_NAME).containsKey(msgId)){
            //redis中包含该key,说明该消息已经被消费过
            logger.info(msgId+":消息已经被消费");
            //确认消息被消费
            channel.basicAck(tag, false);
            return;
        }
        //收到消息，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            //访客邮箱地址
            helper.setTo(visitors.getEmail());
            //服务器邮箱地址
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
            //邮件发送成功
            redisTemplate.opsForHash().put(Constants.MAIL_QUEUE_NAME,msgId,"null");
            //手动清空消息队列
            channel.basicAck(tag, false);
        }catch (MessagingException e) {
            //消息发送失败,回到队列等待下一次发送
            channel.basicNack(tag, false, true);
            e.printStackTrace();
            logger.error("邮件发送失败："+e.getMessage());
        }
    }

}
