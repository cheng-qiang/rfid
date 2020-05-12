package org.chen.rfid.service.station.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.chen.rfid.mapper.station.VisitorsMapper;
import org.chen.rfid.model.MailSendLog;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.station.Visitors;
import org.chen.rfid.model.vo.VisitorsVo;
import org.chen.rfid.service.station.VisitorsService;
import org.chen.rfid.service.system.impl.MailSendLogServiceImpl;
import org.chen.rfid.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 程强
 * @date 2020年03月25日 10:24
 * @Description:
 */
@Service
public class VisitorsServiceImpl implements VisitorsService {
    private static final Logger logger = LoggerFactory.getLogger(VisitorsServiceImpl.class);
    @Autowired
    private VisitorsMapper visitorsMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailSendLogServiceImpl sendLogService;

    @Override
    public Object getAllVisitors(Integer page, Integer size, String name) {
        Page<Visitors> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Visitors> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Visitors::getName, name);
        IPage<Visitors> pageResult = visitorsMapper.selectPage(pageParam, queryWrapper);
        return pageResult;
    }

    @Override
    public RespBean deleteVisitor(Long id) {
        if (visitorsMapper.deleteById(id)==1){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    @Override
    public RespBean deleteVisitors(Long[] ids) {
        ArrayList<Long> arrayList = new ArrayList<>(ids.length);
        Collections.addAll(arrayList, ids);
        if (visitorsMapper.deleteBatchIds(arrayList)==ids.length){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    @Override
    public RespBean addVisitors(Visitors visitors) {
        if (visitorsMapper.insert(visitors)==1){
            String msgId = UUID.randomUUID().toString();
            MailSendLog mailSendLog = new MailSendLog();
            mailSendLog.setMsgId(msgId);
            mailSendLog.setCreateTime(new Date());
            mailSendLog.setExchange(Constants.MAIL_EXCHANGE_NAME);
            mailSendLog.setRouteKey(Constants.MAIL_ROUTING_KEY_NAME);
            mailSendLog.setEmpId(visitors.getId());
            mailSendLog.setTryTime(new Date(System.currentTimeMillis()+1000 * 60 * Constants.MSG_TIMEOUT));
            sendLogService.insert(mailSendLog);
            rabbitTemplate.convertAndSend(Constants.MAIL_EXCHANGE_NAME,Constants.MAIL_ROUTING_KEY_NAME,visitors,new CorrelationData(msgId));
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    @Override
    public RespBean updateVisitors(Visitors visitors) {
        if (visitorsMapper.updateById(visitors)==1){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    @Override
    public List<VisitorsVo> findLastVisitors() {
        return visitorsMapper.findLastVisitors();
    }

    @Override
    public Visitors selectOne(Long id) {
        return visitorsMapper.selectById(id);
    }
}
