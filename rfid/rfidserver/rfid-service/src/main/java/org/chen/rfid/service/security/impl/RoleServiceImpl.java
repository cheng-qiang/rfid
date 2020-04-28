package org.chen.rfid.service.security.impl;

import org.chen.rfid.mapper.security.RoleMapper;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.security.Role;
import org.chen.rfid.service.security.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 程强
 * @date 2020年01月16日 15:50
 * @Description:
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    public List<Role> getAllRoles() {
        List<Role> roles = roleMapper.selectList(null);
        return roles;
    }

    public RespBean insertRole(Role role) {
        if (roleMapper.insert(role) != 1){
            return RespBean.error("添加失败！");
        }
        return RespBean.ok("添加成功！");
    }

    @Transactional
    public RespBean deleteRole(Long role_id) {
        roleMapper.deleteMenuRoleByRoleId(role_id);
        roleMapper.deleteUserRoleByRoleId(role_id);
        if (roleMapper.deleteRoleByRoleId(role_id) != 1){
            return RespBean.error("删除失败！");
        }
        return RespBean.ok("删除成功！");
    }
}
