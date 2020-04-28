package org.chen.rfid.mapper.security;

import org.apache.ibatis.annotations.Param;

/**
 * @author 程强
 * @date 2020年01月16日 16:02
 * @Description:
 */
public interface MenuRoleMapper {
    /**
     * 根据角色id
     * @param rid
     */
    void deleteByRid(Long rid);

    int insertRecord(@Param("rid") Long rid, @Param("mids") Long[] mids);
}
