package org.chen.rfid.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 言少钱
 * @date 2020年05月06日 20:25
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@ApiModel(value = "最近访客量对象")
@Data
public class VisitorsVo {

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date days;
    @ApiModelProperty("访客量")
    private Integer count;

}
