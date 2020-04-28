package org.chen.rfid.util;

import org.chen.rfid.model.security.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 程强
 * @date 2020年03月23日 10:15
 * @Description:
 */
public class UserUtil {
    public static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
