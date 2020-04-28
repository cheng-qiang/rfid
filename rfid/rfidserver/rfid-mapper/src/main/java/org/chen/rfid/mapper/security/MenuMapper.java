package org.chen.rfid.mapper.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.chen.rfid.model.security.Menu;

import java.util.List;

/**
 * @author 程强
 * @date 2020年01月16日 9:34
 * @Description:
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id获取所有的菜单
     * @param user_id
     * @return
     */
    List<Menu> getMenusByUserId(Long user_id);

    /**
     * 获取所有的菜单带有的角色
     * @return
     */
    List<Menu> getAllMenusWithRole();

    /**
     * 获取所有资源
     * @return
     */
    List<Menu> getAllMenus();

    /**
     * 根据角色id获取资源id
     * @param rid
     * @return
     */
    List<Long> getMidsByRid(Long rid);
}
