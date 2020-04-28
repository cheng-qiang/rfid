package org.chen.rfid.controller.chat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.chen.rfid.model.ChatMsg;
import org.chen.rfid.model.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月27日 11:05
 * @Description:
 */
@Controller
@Api(tags = "在线聊天消息处理接口")
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    @ApiOperation(value = "在线聊天消息处理接口")
    public void handleMsg(Authentication authentication, ChatMsg chatMsg){
        logger.info(chatMsg.toString());
        User user = (User) authentication.getPrincipal();
        chatMsg.setFrom(user.getUsername());
        chatMsg.setFromNickname(user.getName());
        chatMsg.setDate(new Date());
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
    }
}
