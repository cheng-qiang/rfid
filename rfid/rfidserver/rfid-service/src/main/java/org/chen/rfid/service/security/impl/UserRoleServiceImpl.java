package org.chen.rfid.service.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.chen.rfid.mapper.security.UserRoleMapper;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.security.UserRole;
import org.chen.rfid.service.security.UserRoleService;

import org.chen.rfid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 程强
 * @date 2020年03月24日 9:37
 * @Description:
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public RespBean updateUserRole(Long u_rid, Long[] rids) {
        //删除
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUser_id,u_rid);
        userRoleMapper.delete(wrapper);
        //添加
        if(userRoleMapper.addUserRole(u_rid,rids) == rids.length){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }
}
