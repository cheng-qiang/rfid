package org.chen.rfid.model.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 程强
 * @date 2020年01月16日 9:32
 * @Description:
 */
@Getter
@Setter
public class Meta{
    private Boolean keepalive;
    private Boolean requireAuth;
}
