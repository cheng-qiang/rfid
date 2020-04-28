package org.chen.rfid.mapper.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.chen.rfid.model.security.UserRole;

/**
 * @author 程强
 * @date 2020年03月24日 9:35
 * @Description:
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 添加用户扮演的角色
     * @param u_rid 指定某一个用户
     * @param rids 批量添加角色
     * @return
     */
    int addUserRole(@Param("u_rid") Long u_rid, @Param("rids") Long[] rids);
}
