package org.chen.rfid.service.station.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.chen.rfid.mapper.station.VisitorsMapper;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.station.Visitors;
import org.chen.rfid.service.station.VisitorsService;
import org.chen.rfid.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

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

    @Override
    public Object getAllVisitors(Integer page, Integer size, String name) {
        Page<Visitors> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Visitors> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Visitors::getName, name);
        IPage<Visitors> pageResult = visitorsMapper.selectPage(pageParam, queryWrapper);
        // logger.info(pageResult.getRecords().toString());
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
            rabbitTemplate.convertAndSend("chen.mail.visitors", visitors);
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
}
