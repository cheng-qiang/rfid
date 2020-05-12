package org.chen.rfid.controller.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.annotation.SysLog;
import org.chen.rfid.model.security.Role;
import org.chen.rfid.model.security.User;
import org.chen.rfid.service.security.impl.RoleServiceImpl;
import org.chen.rfid.service.security.impl.UserRoleServiceImpl;
import org.chen.rfid.service.security.impl.UserServiceImpl;
import org.chen.rfid.util.FastDFSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 程强
 * @date 2020年03月23日 15:46
 * @Description:
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "业务操作员接口")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "获取当前用户信息接口")
    @GetMapping("/user/info")
    public User getCurrentUser(Authentication authentication){
        return ((User) authentication.getPrincipal());
    }

    @PutMapping("/user/info")
    public RespBean updateUser(@RequestBody User user, Authentication authentication) {
        if (userService.updateCurrentUser(user) == 1) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authentication.getAuthorities()));
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @PutMapping("/user/pass")
    public RespBean updateUserPassword(@RequestBody Map<String, Object> info) {
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        String user_Id = (String) info.get("userId");
        Long userId = Long.valueOf(user_Id);
        if (userService.updateUserPassword(oldPass, pass, userId)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @PostMapping("/user/face")
    public RespBean updateHrUserFace(MultipartFile file, Long id, Authentication authentication) {
        String fileId = FastDFSUtils.upload(file);
        String url = null;
        try {
            url = FastDFSUtils.getFileAddressWithToken(fileId);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("更新失败!");
        }
        if (userService.updateUserFace(url, id) == 1) {
            User user = (User) authentication.getPrincipal();
            user.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authentication.getAuthorities()));
            return RespBean.ok("更新成功!", url);
        }
        return RespBean.error("更新失败!");
    }


    @GetMapping("/")
    @ApiOperation(value = "获取系统登录用户列表接口",notes = "可关键字查询")
    public List<User> getAllUsers(String keyWords){
        return userService.getAllUsers(keyWords);
    }

    @SysLog(value = "更新用户")
    @PutMapping("/")
    @ApiOperation(value = "更新用户通用接口",notes = "此处需要注意前端提交用户信息删除user.roles,后端忽略掉Authorities(@JsonIgnore)")
    public RespBean updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @GetMapping("/roles")
    @ApiOperation(value = "获取所有角色接口",notes = "此处接口用于渲染角色列表")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @SysLog(value = "删除操作员")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户接口",notes = "通过用户ID删除用户信息")
    public RespBean deleteUser(@PathVariable("id")Long id){
        return userService.deleteUser(id);
    }

    @SysLog(value = "更新用户角色")
    @PutMapping("/role")
    @ApiOperation(value = "更新用户角色接口",notes = "更新user_role表，先根据u_rid删除user_role表中记录，再根据u_rid更新表")
    public RespBean updateUserRole(Long u_rid,Long[] rids){
        return userRoleService.updateUserRole(u_rid,rids);
    }
}
