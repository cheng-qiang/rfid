package org.chen.rfid.model.station;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.chen.rfid.model.vo.PerVisitorTabVo;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月31日 15:55
 * @Description:
 */
@Data
@TableName("t_rfid_personnel")
@ApiModel(value = "t_rfid_personnel实体类",description = "制卡管理描述信息类")
public class Personnel {
    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @ApiModelProperty("创建时间")
    private Date createDate;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @ApiModelProperty("开始时间")
    private Date startDate;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @ApiModelProperty("结束时间")
    private Date endDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @ApiModelProperty("还卡时间")
    private Date backDate;
    @NotNull
    @ApiModelProperty("工作描述")
    private Integer status;
    @NotNull
    @ApiModelProperty("工作描述")
    private String work;
    @ApiModelProperty("其他制卡信息")
    private PerVisitorTabVo perVisitorTabVo;
}
