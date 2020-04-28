package org.chen.rfid.model.station;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.chen.rfid.model.validator.IsIPAddress;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月21日 10:24
 * @Description:
 */
@Data
@TableName("t_station")
@ApiModel(value = "t_station实体类",description = "定位基站信息描述类")
public class Station implements Serializable {
    @ApiModelProperty("id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("编号")
    @NotNull
    private String code;

    @ApiModelProperty("名称")
    @NotNull
    private String name;

    @ApiModelProperty("版本号")
    @NotNull
    private String version;

    @ApiModelProperty("IP地址")
    @IsIPAddress
    private String ip;

    @ApiModelProperty("状态（0：停用，1：启用）")
    private Boolean status;

    @ApiModelProperty("创建日期")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date date;

    public Station(){

    }

    public Station(@NotNull String code, @NotNull String name, @NotNull String version, @IsIPAddress String ip, Boolean status, @NotNull Date date) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.ip = ip;
        this.status = status;
        this.date = date;
    }
}
