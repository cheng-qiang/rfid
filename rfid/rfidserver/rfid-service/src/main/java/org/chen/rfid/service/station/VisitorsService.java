package org.chen.rfid.service.station;

import org.apache.ibatis.annotations.Param;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.station.Visitors;
import org.chen.rfid.model.vo.VisitorsVo;

import java.util.List;

/**
 * @author 程强
 * @date 2020年03月25日 10:23
 * @Description:
 */
public interface VisitorsService {

    /**
     * 分页条件查询访客信息
     * @param page 当前页
     * @param size 每页数
     * @param name 访客名称
     * @return
     */
    Object getAllVisitors(Integer page, Integer size, String name);

    /**
     * 删除访客
     * @param id 指定访客
     * @return
     */
    RespBean deleteVisitor(Long id);

    /**
     * 批量删除访客
     * @param ids
     * @return
     */
    RespBean deleteVisitors(@Param("ids") Long[] ids);

    /**
     * 添加访客
     * @param visitors
     * @return
     */
    RespBean addVisitors(Visitors visitors);

    /**
     * 更新访客
     * @param visitors
     * @return
     */
    RespBean updateVisitors(Visitors visitors);

    /**
     * 统计最近7天访客量
     * @return
     */
    List<VisitorsVo> findLastVisitors();

    /**
     * id查询访客
     * @return
     */
    Visitors selectOne(Long id);
}
