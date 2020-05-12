package org.chen.rfid.mapper.station;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.chen.rfid.model.station.Personnel;

import java.util.Date;
import java.util.List;

/**
 * @author 程强
 * @date 2020年04月11日 7:49
 * @Description:
 */
public interface CardMapper extends BaseMapper<Personnel> {

    /**
     * 分页查找制卡列表信息
     * @param page
     * @param size
     * @param personnel
     * @param beginDateScope
     * @return
     */
    List<Personnel> findPersonnelByPage(@Param("page")Integer page,@Param("size") Integer size,@Param("ps") Personnel personnel,@Param("beginDateScope") Date[] beginDateScope);

    /**
     * 获取制卡信息总记录数
     * @param personnel
     * @param beginDateScope
     * @return
     */
    Long getTotal(@Param("ps") Personnel personnel,@Param("beginDateScope") Date[] beginDateScope);


}
