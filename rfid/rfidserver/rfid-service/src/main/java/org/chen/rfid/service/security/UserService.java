package org.chen.rfid.service.security;

import org.chen.rfid.model.security.User;

import java.util.List;

/**
 * @author 程强
 * @date 2020年03月24日 9:38
 * @Description:
 */
public interface UserService {

    /**
     * 获取所有用户，除去当前登录系统用户
     * @return
     */
    List<User> getAllUsersExceptCurrentUser();
}
