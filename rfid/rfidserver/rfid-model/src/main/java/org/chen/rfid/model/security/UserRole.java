package org.chen.rfid.model.security;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 程强
 * @date 2020年03月24日 9:29
 * @Description:
 */
@Data
@TableName("user_role")
@ApiModel(value = "user_role实体类",description = "用户和角色关联信息表")
public class UserRole {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "外键用户ID")
    @TableField(value = "userid")
    private Long user_id;
    @ApiModelProperty(value = "外键角色ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long rid;

    public UserRole(){

    }

    public UserRole(Long user_id, Long rid) {
        this.user_id = user_id;
        this.rid = rid;
    }
}
