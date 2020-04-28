package org.chen.rfid.mapper.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.chen.rfid.model.security.Role;

/**
 * @author 程强
 * @date 2020年01月16日 16:14
 * @Description:
 */
public interface RoleMapper extends BaseMapper<Role> {

    int deleteMenuRoleByRoleId(Long role_id);

    int deleteUserRoleByRoleId(Long role_id);

    int deleteRoleByRoleId(Long role_id);
}
