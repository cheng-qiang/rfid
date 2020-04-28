package org.chen.rfid.model.station;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.chen.rfid.model.validator.IsIdentityNumber;
import org.chen.rfid.model.validator.IsPhone;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月25日 9:24
 * @Description:
 */
@Data
@TableName("t_visitors")
@ApiModel(value = "t_visitors访客实体类",description = "机房访客信息描述类")
public class Visitors implements Serializable {

    @ApiModelProperty("ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("名称")
    @NotNull
    private String name;
    @ApiModelProperty("电话号码")
    @IsPhone
    private String phone;
    @ApiModelProperty(value = "身份证号",notes = "这里属性定义不能直接使用identity_number，Mybatis-plus映射不到指定字段，使用驼峰")
    @TableField(value = "identity_number")
    @IsIdentityNumber
    private String identityNumber;
    @ApiModelProperty("所属公司")
    private String company;
    @ApiModelProperty("所属部门")
    private String department;
    @ApiModelProperty("邮箱地址")
    @Email
    private String email;
    @ApiModelProperty("创建日期")
    private Date date;

}
