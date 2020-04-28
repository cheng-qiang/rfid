package org.chen.rfid.model.station;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月21日 12:57
 * @Description:
 */
@Data
@TableName("t_tab_station")
@ApiModel(value = "t_tab_station实体类",description = "定位标签信息描述类")
public class TabStation {

    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("编号")
    @NotNull
    private String code;

    @ApiModelProperty("类别型号（）")
    @NotNull
    private Integer type;

    @ApiModelProperty("设备状态（0：故障，1：正常）")
    private Boolean status;

    @ApiModelProperty("标签购买日期")
    @NotNull
    private Date date;
}
