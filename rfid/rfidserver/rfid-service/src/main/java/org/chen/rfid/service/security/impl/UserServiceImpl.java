package org.chen.rfid.service.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.chen.rfid.mapper.security.UserMapper;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.security.User;
import org.chen.rfid.service.security.UserService;

import org.chen.rfid.util.Constants;
import org.chen.rfid.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 程强
 * @date 2020年01月14日 17:55
 * @Description:
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (null == user){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        user.setRoles(userMapper.getUserRolesByUserId(user.getId()));
        return user;
    }

    public List<User> getAllUsers(String keyWords) {
        //logger.info(keyWords);
        //查询所有系统用户除去自己
        return userMapper.getAllUsers(UserUtil.getCurrentUser().getId(),keyWords);
    }

    public RespBean updateUser(User user) {
        if (userMapper.updateById(user)==1){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    public RespBean deleteUser(Long id) {
        if (userMapper.deleteById(id)==1){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    @Override
    public List<User> getAllUsersExceptCurrentUser() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(User::getId,UserUtil.getCurrentUser().getId());
        List<User> users = userMapper.selectList(queryWrapper);
        return users;
    }

    @Override
    public Integer updateCurrentUser(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public boolean updateUserPassword(String oldPass, String pass, Long userId) {
        User user = userMapper.selectById(userId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldPass, user.getPassword())) {
            String encodePass = encoder.encode(pass);
            Integer result = userMapper.updateUserPassword(userId, encodePass);
            if (result == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer updateUserFace(String url, Long id) {
        return userMapper.updateUserFace(url,id);
    }
}
