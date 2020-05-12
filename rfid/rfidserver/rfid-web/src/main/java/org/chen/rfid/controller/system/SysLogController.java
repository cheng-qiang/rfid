package org.chen.rfid.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.chen.rfid.service.system.impl.SysLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 言少钱
 * @date 2020年05月05日 13:07
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@RestController
@Api(tags = "操作日志接口")
@RequestMapping("/system/interface")
public class SysLogController {

    @Autowired
    private SysLogServiceImpl sysLogService;

    @GetMapping("/")
    @ApiOperation(value = "系统日志列表")
    public Object findSysLogList(@RequestParam(defaultValue = "1")Integer page,
                                 @RequestParam(defaultValue = "10")Integer size,@RequestParam(value = "username",defaultValue = "") String username){
        return sysLogService.findSysLogList(page,size,username);
    }
}
