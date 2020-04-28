package org.chen.rfid.service.station;

import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.station.TabStation;

/**
 * @author 程强
 * @date 2020年03月28日 16:05
 * @Description:
 */
public interface TabStationService {
    /**
     * 标签查询接口
     * @param page
     * @param size
     * @param code
     * @param status
     * @return
     */
   Object getAllTabStations(Integer page,Integer size,String code,Boolean status);

    /**
     * 标签是否存在
     * @param tab_code
     * @return
     */
   String isCodeOnly(String tab_code);

    /**
     * 添加定位标签
     * @param tabStation
     * @return
     */
    RespBean addTabStation(TabStation tabStation);

    /**
     * 更新定位标签
     * @param tabStation
     * @return
     */
    RespBean updateTabStation(TabStation tabStation);

    /**
     * 删除标签
     * @param id
     * @return
     */
    RespBean deleteTabStation(Long id);

    /**
     * 批量删除标签
     * @param ids
     * @return
     */
    RespBean deleteTabStations(Long[] ids);
}
