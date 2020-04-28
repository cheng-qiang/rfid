package org.chen.rfid.controller.security;

import org.chen.rfid.model.RespBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 程强
 * @date 2020年01月14日 18:05
 * @Description:
 */
public class LoginController {

    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登录,请登录！");
    }
}
