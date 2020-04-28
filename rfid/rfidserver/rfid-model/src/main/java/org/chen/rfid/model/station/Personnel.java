package org.chen.rfid.model.station;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

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
    private Long userId;
    @NotNull
    private Long TabStationId;
    @NotNull
    private Long visitorsId;
    @NotNull
    private Date createDate;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    private Date backDate;
    @NotNull
    private Integer status;
    @NotNull
    private String work;
}
