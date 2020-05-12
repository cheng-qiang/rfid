package org.chen.rfid.model.station;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 言少钱
 * @date 2020年05月07日 11:40
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@TableName(value = "per_tab_visitor")
@ApiModel(value = "制卡关系对象")
@Data
public class PerVisitorTab {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("personnerlId")
    private Long personnerlId;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("userId")
    private Long userId;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("tabId")
    private Long tabId;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("visitorId")
    private Long visitorId;
}
