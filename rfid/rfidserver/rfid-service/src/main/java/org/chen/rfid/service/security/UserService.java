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

    /**
     * 更新当前登陆用户信息
     * @param user
     * @return
     */
    Integer updateCurrentUser(User user);

    /**
     * 更新用户密码
     * @param oldPass
     * @param pass
     * @param userId
     * @return
     */
    boolean updateUserPassword(String oldPass, String pass, Long userId);

    /**
     * 更新用户头像
     * @param url
     * @param id
     * @return
     */
    Integer updateUserFace(String url, Long id);
}
