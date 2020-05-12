package org.chen.rfid.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.chen.rfid.mapper.system.SysLogMapper;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.system.OpLog;
import org.chen.rfid.service.system.SysLogService;
import org.chen.rfid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 言少钱
 * @date 2020年05月05日 12:14
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public RespBean saveSysLog(OpLog sysLog) {
        if (sysLogMapper.insert(sysLog) == Constants.SUCCESS_NUMBER){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    @Override
    public Object findSysLogList(Integer page,Integer size,String username) {
        Page<OpLog> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<OpLog> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(OpLog::getUsername,username);
        IPage<OpLog> opLogs = sysLogMapper.selectPage(pageParam, queryWrapper);
        return opLogs;
    }
}
