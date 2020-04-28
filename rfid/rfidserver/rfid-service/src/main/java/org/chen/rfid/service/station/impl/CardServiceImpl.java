package org.chen.rfid.service.station.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.chen.rfid.mapper.security.UserMapper;
import org.chen.rfid.mapper.station.CardMapper;
import org.chen.rfid.mapper.station.TabStationMapper;
import org.chen.rfid.mapper.station.VisitorsMapper;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.RespPageBean;
import org.chen.rfid.model.station.Personnel;
import org.chen.rfid.model.station.TabStation;
import org.chen.rfid.model.station.Visitors;
import org.chen.rfid.service.station.CardService;
import org.chen.rfid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 程强
 * @date 2020年04月10日 16:33
 * @Description:
 */
@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private TabStationMapper tabStationMapper;

    @Autowired
    private VisitorsMapper visitorsMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public RespPageBean getCardByPage(Integer page, Integer size, String tabStationCode, String visitorName, Long userId,Integer status, Date[] createDate) {
        RespPageBean bean = new RespPageBean();
        Page<Personnel> pageParam = new Page<>(page,size);
        LambdaQueryWrapper<Personnel> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(tabStationCode)|| !StringUtils.isEmpty(visitorName)){
            LambdaQueryWrapper<TabStation> queryWrapperTabStation = new LambdaQueryWrapper<>();
            queryWrapperTabStation.like(TabStation::getCode,tabStationCode);
            List<TabStation> tabStations = tabStationMapper.selectList(queryWrapperTabStation);
            LambdaQueryWrapper<Visitors> queryWrapperVisitors = new LambdaQueryWrapper<>();
            queryWrapperVisitors.like(Visitors::getName,visitorName);
            List<Visitors> visitors = visitorsMapper.selectList(queryWrapperVisitors);
            if (null != tabStations || tabStations.size() > 0 || null != visitors || visitors.size() > 0){
                List<Long> tabStationIds =new ArrayList<>();
                for (TabStation tabStation : tabStations) {
                    tabStationIds.add(tabStation.getId());
                }
                List<Long> visitorsIds =new ArrayList<>();
                for (Visitors visitor : visitors){
                    visitorsIds.add(visitor.getId());
                }
                queryWrapper.in(Personnel::getTabStationId,tabStationIds).in(Personnel::getVisitorsId,visitorsIds);
                return getResultPersonnel(pageParam,queryWrapper,bean);
            }
            //高级搜索条件构造
        }else if (userId != null || status != null){
            System.out.println(userId+">>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(status+"====================");
            queryWrapper.eq(Personnel::getUserId,userId).eq(Personnel::getStatus,status).between(Personnel::getCreateDate,createDate[0],createDate[1]);
            return getResultPersonnel(pageParam,queryWrapper,bean);
        }
        return getResultPersonnel(pageParam,queryWrapper,bean);
    }

    @Override
    public RespBean addCard(Personnel personnel) {
        int insert = cardMapper.insert(personnel);
        if (insert == Constants.SUCCESS_NUMBER){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    @Override
    public RespBean editCard(Personnel personnel) {
        int i = cardMapper.updateById(personnel);
        if (i == Constants.SUCCESS_NUMBER){
            return RespBean.ok(Constants.SUCCESS);
        }
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

    /**
     * 组装制卡管理列表返回结果
     * @param pageParam
     * @param queryWrapper
     * @param bean
     * @return
     */
    public RespPageBean getResultPersonnel(Page<Personnel> pageParam,LambdaQueryWrapper<Personnel> queryWrapper,RespPageBean bean){
        Page<Personnel> personnelPage = cardMapper.selectPage(pageParam, queryWrapper);
        List<Personnel> records = personnelPage.getRecords();
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (Personnel p : records) {
            Map<String,Object> PersonnelMap = new HashMap<>();
            PersonnelMap.put("id", p.getId());
            PersonnelMap.put("tabCode", tabStationMapper.selectById(p.getTabStationId()).getCode());
            PersonnelMap.put("visitorsName", visitorsMapper.selectById(p.getVisitorsId()).getName());
            PersonnelMap.put("work", p.getWork());
            PersonnelMap.put("createDate", p.getCreateDate());
            PersonnelMap.put("startDate", p.getStartDate());
            PersonnelMap.put("endDate", p.getEndDate());
            PersonnelMap.put("backDate", p.getBackDate());
            PersonnelMap.put("status", p.getStatus());
            PersonnelMap.put("userName", userMapper.selectById(p.getUserId()).getName());
            resultList.add(PersonnelMap);
        }
        bean.setData(resultList);
        bean.setTotal(personnelPage.getTotal());
        return bean;
    }

}
