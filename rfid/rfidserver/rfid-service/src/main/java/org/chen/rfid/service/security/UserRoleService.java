package org.chen.rfid.service.security;

import org.chen.rfid.model.RespBean;


/**
 * @author 程强
 * @date 2020年03月24日 9:38
 * @Description:
 */
public interface UserRoleService {

    /**
     * 更新用户扮演的角色
     * @param u_rid
     * @param rids
     * @return
     */
    RespBean updateUserRole(Long u_rid, Long[] rids);
}
