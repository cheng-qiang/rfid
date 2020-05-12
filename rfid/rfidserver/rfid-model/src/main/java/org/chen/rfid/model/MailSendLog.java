package org.chen.rfid.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 言少钱
 * @date 2020年05月11日 16:29
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@Data
@ApiModel(value = "邮件发送日志对象")
public class MailSendLog {

    @ApiModelProperty("消息ID")
    private String msgId;

    private Long empId;

    @ApiModelProperty("0发送中，1发送成功，2发送失败")
    private  Integer status;

    private String routeKey;

    private String exchange;

    @ApiModelProperty("重试次数")
    private Integer count;

    @ApiModelProperty("第一次重试时间")
    private Date tryTime;

    private Date createTime;

    private Date updateTime;
}
