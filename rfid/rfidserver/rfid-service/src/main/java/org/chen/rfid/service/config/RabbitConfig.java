package org.chen.rfid.service.config;

import org.chen.rfid.service.system.impl.MailSendLogServiceImpl;
import org.chen.rfid.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 言少钱
 * @date 2020年05月11日 16:37
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * 消息可靠性：https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247487938&idx=1&sn=53ac42b8c30fe6be1ea2e294577b54d5&scene=21#wechat_redirect
 * 消息重复消费：https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247487942&idx=1&sn=884813db0219bbcbe1ad6c4d682a146e&scene=21#wechat_redirect
 * @Description:
 */
@Configuration
public class RabbitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    MailSendLogServiceImpl mailSendLogService;

    @Bean
    RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //确认消息是否发送成功
        rabbitTemplate.setConfirmCallback((data,ack,cause)->{
            if (ack){
                String msgId = data.getId();
                logger.info(msgId+"： 消息发送成功");
                //修改数据库中的记录，消息投递成功
                mailSendLogService.updateMailSendLogStatus(msgId,1);
            }else {
                logger.info( "消息发送失败！");
            }
        });
        rabbitTemplate.setReturnCallback((msg,repCode,repText,exchange,routingkey)->{
            logger.info( "消息发送失败！");
        });
        return rabbitTemplate;
    }

    @Bean
    Queue mailQueue(){
        return new Queue(Constants.MAIL_QUEUE_NAME,true);
    }

    @Bean
    DirectExchange mailExchange(){
        return new DirectExchange(Constants.MAIL_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding mailBinding(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(Constants.MAIL_ROUTING_KEY_NAME);
    }

}




























