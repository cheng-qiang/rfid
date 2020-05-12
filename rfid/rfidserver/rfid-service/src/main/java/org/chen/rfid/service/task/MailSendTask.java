package org.chen.rfid.service.task;

import org.chen.rfid.model.MailSendLog;
import org.chen.rfid.model.station.Visitors;
import org.chen.rfid.service.station.impl.VisitorsServiceImpl;
import org.chen.rfid.service.system.impl.MailSendLogServiceImpl;
import org.chen.rfid.util.Constants;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author 言少钱
 * @date 2020年05月11日 17:26
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * cron表达式：https://cron.qqe2.com/
 * @Description:保证消息发送成功
 */
@Component
public class MailSendTask {
    @Autowired
    MailSendLogServiceImpl mailSendLogService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    VisitorsServiceImpl visitorsService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void  mailResendTask(){
        List<MailSendLog> logs = mailSendLogService.findMailSendLogByStatus();
        //logs.forEach((e)->System.out.println(e));
        logs.forEach(mailSendLog -> {
            if (mailSendLog.getCount() >= Constants.MAX_TRY_COUNT){
                //直接设置该条消息发送失败
                mailSendLogService.updateMailSendLogStatus(mailSendLog.getMsgId(), 2);
            }else {
                mailSendLogService.updateCount(mailSendLog.getMsgId(),new Date());
                Visitors visitors = visitorsService.selectOne(mailSendLog.getEmpId());
                rabbitTemplate.convertAndSend(Constants.MAIL_EXCHANGE_NAME,Constants.MAIL_ROUTING_KEY_NAME,visitors,new CorrelationData(mailSendLog.getMsgId()));
            }
        });
    }
}
