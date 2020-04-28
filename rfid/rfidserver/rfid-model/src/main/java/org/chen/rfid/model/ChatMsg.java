package org.chen.rfid.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月27日 11:09
 * @Description:
 */
@ApiModel(value = "在线聊天消息封装类")
@Data
public class ChatMsg {
    @ApiModelProperty(value = "消息从哪里")
    private String from;
    @ApiModelProperty(value = "消息到哪去")
    private String to;
    @ApiModelProperty(value = "消息内容")
    private String content;
    @ApiModelProperty(value = "消息发送时间")
    private Date date;
    private String fromNickname;
}
