package org.chen.rfid.model.security;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 程强
 * @date 2020年01月16日 9:28
 * @Description:
 */
@Getter
@Setter
@TableName("t_menu")
@ApiModel(value = "t_menu实体类",description = "资源信息描述类")
public class Menu {
    @ApiModelProperty("资源id")
    private Long id;
    @ApiModelProperty("资源接口地址")
    private String url;
    @ApiModelProperty("资源目录路径")
    private String path;
    @ApiModelProperty("资源组件名称（对应vue组件名称）")
    private String component;
    @ApiModelProperty("资源中文名称（动态渲染资源展示）")
    private String name;
    @ApiModelProperty("资源图标（http://fontawesome.dashgame.com/）")
    private String iconCls;
    @ApiModelProperty("资源父级id")
    private Long parentId;
    @ApiModelProperty("资源是否可用（1：可用 0：不可用）")
    private Boolean enabled;
    @ApiModelProperty("资源其他信息描述")
    private Meta meta;
    @ApiModelProperty("资源子级")
    private List<Menu> children;
    @ApiModelProperty("资源扮演的角色")
    private List<Role> roles;
}
