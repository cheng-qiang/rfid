package org.chen.rfid.service.security.impl;

import org.chen.rfid.mapper.security.MenuMapper;
import org.chen.rfid.mapper.security.MenuRoleMapper;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.security.Menu;
import org.chen.rfid.model.security.User;
import org.chen.rfid.service.security.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 程强
 * @date 2020年01月16日 9:37
 * @Description:
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getMenusByUserId(){
        return menuMapper.getMenusByUserId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return  menuMapper.getAllMenus();
    }

    public List<Long> getMidsByRid(Long rid) {
        return  menuMapper.getMidsByRid(rid);
    }

    @Transactional
    public RespBean updateMenuRoles(Long rid, Long[] mids) {
        if (mids == null){
            menuRoleMapper.deleteByRid(rid);
            return RespBean.ok("更新成功！");
        }
        menuRoleMapper.deleteByRid(rid);
        if (menuRoleMapper.insertRecord(rid,mids) != mids.length){
            return RespBean.error("更新失败！");
        }
        return RespBean.ok("更新成功！");
    }
}
