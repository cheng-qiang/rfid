package org.chen.rfid.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.chen.rfid.model.system.OpLog;

/**
 * @author 言少钱
 * @date 2020年05月05日 12:15
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@Mapper
public interface SysLogMapper extends BaseMapper<OpLog> {
}
