package org.chen.rfid.service.system.impl;

import org.chen.rfid.mapper.system.MailSendLogMapper;
import org.chen.rfid.model.MailSendLog;
import org.chen.rfid.service.system.MailSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 言少钱
 * @date 2020年05月11日 16:36
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@Service
public class MailSendLogServiceImpl implements MailSendLogService {
    @Autowired
    private MailSendLogMapper mailSendLogMapper;

    @Override
    public Integer updateMailSendLogStatus(String msgId, Integer status) {
        return mailSendLogMapper.updateMailSendLogStatus(msgId,status);
    }

    @Override
    public Integer insert(MailSendLog mailSendLog) {
        return mailSendLogMapper.insert(mailSendLog);
    }

    @Override
    public List<MailSendLog> findMailSendLogByStatus() {
        return mailSendLogMapper.findMailSendLogByStatus();
    }

    @Override
    public Integer updateCount(String msgId, Date date) {
        return mailSendLogMapper.updateCount(msgId,date);
    }
}
