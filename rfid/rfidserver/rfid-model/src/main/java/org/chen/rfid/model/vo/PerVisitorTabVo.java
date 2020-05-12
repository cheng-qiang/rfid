package org.chen.rfid.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 言少钱
 * @date 2020年05月09日 8:41
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@ApiModel("制卡管理映射类")
@Data
public class PerVisitorTabVo {

    private String username;

    private String code;

    private String visitor;

}
