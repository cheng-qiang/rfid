package org.chen.rfid.controller.system.basic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.annotation.SysLog;
import org.chen.rfid.model.security.Menu;
import org.chen.rfid.model.security.Role;
import org.chen.rfid.service.security.impl.MenuServiceImpl;
import org.chen.rfid.service.security.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 程强
 * @date 2020年01月16日 15:49
 * @Description:
 */
@RestController
@RequestMapping("/system/basic/permiss")
@Api(tags = "权限组相关接口")
public class PermissController {
    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    MenuServiceImpl menuService;

    @GetMapping("/")
    @ApiOperation("获取所有角色的接口")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @GetMapping("/menus")
    @ApiOperation("获取所有菜单的接口")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }

    @GetMapping("/mids/{rid}")
    @ApiOperation("根据角色id获取所有菜单id的接口")
    @ApiImplicitParam(name = "rid",value = "角色id",defaultValue = "99",required = true)
    public List<Long> getMidsByRid(@PathVariable Long rid){
        return  menuService.getMidsByRid(rid);
    }

    @PutMapping("/")
    @SysLog(value = "更新权限")
    @ApiOperation(value = "更新menu_role表的接口",notes = "根据角色id删除所有相关的menu_role记录，再通过角色id添加其能访问的所有资源id，更新menu_role表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid",value = "角色id",required = true),
            @ApiImplicitParam(name = "mids",value = "所有资源id",required = true)
    })
    public RespBean updateMenuRoles(Long rid, Long[] mids){
        return menuService.updateMenuRoles(rid,mids);
    }

    @SysLog(value = "添加角色")
    @PostMapping("/")
    @ApiOperation(value = "添加角色",notes = "添加角色的接口")
    public RespBean insertRole(@RequestBody Role role){
        return roleService.insertRole(role);
    }

    @SysLog(value = "删除角色")
    @DeleteMapping("/{role_id}")
    @ApiOperation(value = "删除角色",notes = "根据角色id删除角色的接口")
    public RespBean deleteRole(@PathVariable Long role_id){
        return roleService.deleteRole(role_id);
    }
}
