package org.chen.rfid.controller.security;

import io.swagger.annotations.Api;
import org.chen.rfid.model.security.Menu;
import org.chen.rfid.service.security.impl.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author 程强
 * @date 2020年01月16日 9:38
 * @Description:
 */
@RestController
@RequestMapping("/system/config")
@Api(tags = "系统初始化接口")
public class SystemConfigController {

    @Autowired
    private MenuServiceImpl menuService;

    @GetMapping("/menu")
    @ApiIgnore
    public List<Menu> getMenusByUserId(){
        return menuService.getMenusByUserId();
    }
}
