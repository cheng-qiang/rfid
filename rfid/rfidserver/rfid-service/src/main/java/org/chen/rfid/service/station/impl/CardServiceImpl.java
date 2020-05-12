package org.chen.rfid.service.station.impl;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.chen.rfid.mapper.security.UserMapper;
import org.chen.rfid.mapper.station.CardMapper;
import org.chen.rfid.mapper.station.TabStationMapper;
import org.chen.rfid.mapper.station.VisitorsMapper;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.RespPageBean;
import org.chen.rfid.model.station.Personnel;
import org.chen.rfid.service.station.CardService;
import org.chen.rfid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.sun.org.slf4j.internal.LoggerFactory.getLogger;

/**
 * @author 程强
 * @date 2020年04月10日 16:33
 * @Description:
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardMapper cardMapper;

    @Override
    public RespPageBean findPersonnelByPage(Integer page, Integer size, Personnel personnel, Date[] beginDateScope) {
        System.out.println(personnel);

        if (page != null && size != null){
            page = (page-1) * size;
        }
        List<Personnel> data = cardMapper.findPersonnelByPage(page, size, personnel, beginDateScope);
        Long total = cardMapper.getTotal(personnel, beginDateScope);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }


    @Override
    public RespPageBean getCardByPage(Integer page, Integer size, String tabStationCode, String visitorName, Long userId,Integer status, Date[] createDate) {

        return null;
    }

    @Override
    public RespBean addCard(Personnel personnel) {
        System.out.println(personnel);

        return RespBean.error(Constants.ERROR);
    }

    @Override
    public RespBean editCard(Personnel personnel) {
        System.out.println(personnel);
        // int i = cardMapper.updateById(personnel);
        // if (i == Constants.SUCCESS_NUMBER){
        //     return RespBean.ok(Constants.SUCCESS);
        // }
        return RespBean.error(Constants.ERROR);
    }

    @Override
    public RespBean deleteCard(Long id) {
        if (cardMapper.deleteById(id)==Constants.SUCCESS_NUMBER){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    @Override
    public RespBean deleteCards(Long[] ids) {
        if (cardMapper.deleteBatchIds(Arrays.asList(ids)) == ids.length){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

}
