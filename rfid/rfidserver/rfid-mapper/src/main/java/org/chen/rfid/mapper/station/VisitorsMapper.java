package org.chen.rfid.mapper.station;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.chen.rfid.model.station.Visitors;
import org.chen.rfid.model.vo.VisitorsVo;

import java.util.List;

/**
 * @author 程强
 * @date 2020年03月25日 10:25
 * @Description:
 */
public interface VisitorsMapper extends BaseMapper<Visitors> {
    /**
     * 统计最近7天访客量
     * @return
     */
    List<VisitorsVo> findLastVisitors();
}
