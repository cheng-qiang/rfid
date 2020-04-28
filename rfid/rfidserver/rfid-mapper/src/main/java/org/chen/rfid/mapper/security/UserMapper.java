package org.chen.rfid.mapper.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.chen.rfid.model.security.Role;
import org.chen.rfid.model.security.User;

import java.util.List;

/**
 * @author 程强
 * @date 2020年01月14日 16:24
 * @Description:
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询当前系统用户扮演的角色
     * @param user_id
     * @return
     */
    List<Role> getUserRolesByUserId(Long user_id);

    /**
     * 查询所用系统用户除去当前登录系统用户
     * @param id 指定系统用户
     * @param keyWords 关键字搜索-->用户名称
     * @return
     */
    List<User> getAllUsers(@Param("id") long id,@Param("keyWords") String keyWords);
}
