package org.chen.rfid.service.system;

import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.system.OpLog;

/**
 * @author 言少钱
 * @date 2020年05月05日 12:19
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
public interface SysLogService {
    /**
     * 保存系统操作日志
     * @param sysLog
     * @return
     */
    RespBean saveSysLog(OpLog sysLog);

    /**
     * 系统操作日志列表
     * @param page
     * @param size
     * @param username
     * @return
     */
    Object findSysLogList(Integer page,Integer size,String username);
}
