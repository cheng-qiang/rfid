package org.chen.rfid.service.station.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.chen.rfid.mapper.station.TabStationMapper;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.station.TabStation;
import org.chen.rfid.service.station.TabStationService;
import org.chen.rfid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author 程强
 * @date 2020年03月28日 16:06
 * @Description:
 */
@Service
public class TabStationServiceImpl implements TabStationService {
    @Autowired
    private TabStationMapper tabStationMapper;

    @Override
    public Object getAllTabStations(Integer pageParam,Integer size,String code,Boolean status) {
        Page<TabStation> page = new Page<>(pageParam,size);
        LambdaQueryWrapper<TabStation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(TabStation::getCode,code);
        if (status!=null){
            queryWrapper.eq(TabStation::getStatus,status);
        }
        IPage<TabStation> pageResult = tabStationMapper.selectPage(page, queryWrapper);
        return pageResult;
    }

    @Override
    public String isCodeOnly(String tab_code) {
        LambdaQueryWrapper<TabStation> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(TabStation::getCode, tab_code);
        TabStation tabStation = tabStationMapper.selectOne(queryWrapper);
        if (tabStation != null){
            return "FALSE";
        }else {
            return "TRUE";
        }
    }

    @Override
    public RespBean addTabStation(TabStation tabStation) {
        if (tabStationMapper.insert(tabStation)==1){
            return RespBean.ok(Constants.SUCCESS);
        }else {
            return RespBean.error(Constants.ERROR);
        }
    }

    @Override
    public RespBean updateTabStation(TabStation tabStation) {
        if (tabStationMapper.updateById(tabStation)==1){
            return RespBean.ok(Constants.SUCCESS);
        }else {
            return RespBean.error(Constants.ERROR);
        }
    }

    @Override
    public RespBean deleteTabStation(Long id) {
        if (tabStationMapper.deleteById(id) ==1){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    @Override
    public RespBean deleteTabStations(Long[] ids) {
        if (tabStationMapper.deleteBatchIds(Arrays.asList(ids))==ids.length){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }
}
