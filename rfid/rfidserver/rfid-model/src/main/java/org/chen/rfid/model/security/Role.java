package org.chen.rfid.model.security;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 程强
 * @date 2020年01月16日 14:40
 * @Description:
 */
@Getter
@Setter
@TableName("t_role")
@ApiModel(value = "t_role实体类",description = "角色信息描述类")
public class Role {
    @TableId
    @ApiModelProperty("角色id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    @ApiModelProperty("角色英文名称(ROLE_)")
    private String name;
    @TableField("nameZh")
    @ApiModelProperty("角色中文名称")
    private String nameZh;
    @TableField("create_time")
    @ApiModelProperty("角色创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @ApiModelProperty("角色备注")
    private String remark;
}
