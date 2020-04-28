package org.chen.rfid.service.exception.custom;

/**
 * @author 程强
 * @date 2020年01月17日 11:19
 * @Description:Redis 连接异常
 */
public class RedisConnectException extends Exception {
    private static final long serialVersionUID = 1639374111871115063L;

    public RedisConnectException(String message) {
        super(message);
    }
}
