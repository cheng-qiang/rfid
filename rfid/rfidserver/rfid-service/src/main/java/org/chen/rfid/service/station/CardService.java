package org.chen.rfid.service.station;

import org.apache.ibatis.annotations.Param;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.RespPageBean;
import org.chen.rfid.model.station.Personnel;

import java.util.Date;
import java.util.List;

/**
 * @author 程强
 * @date 2020年04月10日 16:32
 * @Description:
 */
public interface CardService {

    /**
     * 分页查找制卡列表信息
     * @param page
     * @param size
     * @param personnel
     * @param beginDateScope
     * @return
     */
    RespPageBean findPersonnelByPage(Integer page,Integer size, Personnel personnel,Date[] beginDateScope);









    /**
     * 制卡列表
     * @param page 页
     * @param size 量
     * @param tabStationCode 标签编号
     * @param visitorName 访客名称
     * @param work_date 工作时间
     * @return
     */
    RespPageBean getCardByPage(Integer page, Integer size, String tabStationCode, String visitorName,Long userId,Integer status, Date[] work_date);

    /**
     * 添加绑定接口
     * @param personnel
     * @return
     */
    RespBean addCard(Personnel personnel);

    /**
     * 编辑绑定接口
     * @param personnel
     * @return
     */
    RespBean editCard(Personnel personnel);

    /**
     * 删除绑定
     * @param id
     * @return
     */
    RespBean deleteCard(Long id);

    /**
     * 批量删除绑定
     * @param ids
     * @return
     */
    RespBean deleteCards(Long[] ids);
}
