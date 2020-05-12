package org.chen.rfid.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 言少钱
 * @date 2020年05月05日 11:59
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@Data
@TableName("t_opLog")
@ApiModel(value = "t_opLog操作日志实体类")
public class OpLog {

    @ApiModelProperty("ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("操作日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @TableField(value = "addDate")
    private Date addDate;
    @ApiModelProperty("操作内容")
    private String Operate;
    @ApiModelProperty("操作员id")
    @JsonSerialize(using= ToStringSerializer.class)
    @TableField(value = "userId")
    private Long userId;
    @ApiModelProperty("操作员名称")
    private String username;
}
