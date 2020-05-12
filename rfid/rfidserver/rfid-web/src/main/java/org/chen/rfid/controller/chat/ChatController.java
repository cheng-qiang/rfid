package org.chen.rfid.controller.chat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.chen.rfid.model.security.User;
import org.chen.rfid.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 程强
 * @date 2020年03月27日 10:15
 * @Description:
 */
@RestController
@RequestMapping("/chat")
@Api(tags = "在线聊天接口")
public class ChatController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ApiOperation(value = "获取用户接口",notes = "渲染list组件")
    public List<User> getAllUsers(){
        return userService.getAllUsersExceptCurrentUser();
    }
}
