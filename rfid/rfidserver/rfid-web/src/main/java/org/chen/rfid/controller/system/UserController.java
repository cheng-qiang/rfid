package org.chen.rfid.controller.system;

import io.swagger.annotations.ApiOperation;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.security.Role;
import org.chen.rfid.model.security.User;
import org.chen.rfid.service.security.impl.RoleServiceImpl;
import org.chen.rfid.service.security.impl.UserRoleServiceImpl;
import org.chen.rfid.service.security.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 程强
 * @date 2020年03月23日 15:46
 * @Description:
 */
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    @RequestMapping("/")
    @ApiOperation(value = "获取系统登录用户列表接口",notes = "可关键字查询")
    public List<User> getAllUsers(String keyWords){
        return userService.getAllUsers(keyWords);
    }

    @PutMapping("/")
    @ApiOperation(value = "更新用户通用接口",notes = "此处需要注意前端提交用户信息删除user.roles,后端忽略掉Authorities(@JsonIgnore)")
    public RespBean updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @RequestMapping("/roles")
    @ApiOperation(value = "获取所有角色接口",notes = "此处接口用于渲染角色列表")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户接口",notes = "通过用户ID删除用户信息")
    public RespBean deleteUser(@PathVariable("id")Long id){
        return userService.deleteUser(id);
    }

    @PutMapping("/role")
    @ApiOperation(value = "更新用户角色接口",notes = "更新user_role表，先根据u_rid删除user_role表中记录，再根据u_rid更新表")
    public RespBean updateUserRole(Long u_rid,Long[] rids){
        return userRoleService.updateUserRole(u_rid,rids);
    }
}
