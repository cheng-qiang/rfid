package org.chen.rfid.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 程强
 * @date 2020年01月14日 15:28
 * @Description:
 */
@Getter
@Setter
public class RespBean {
    private Integer status;
    private String msg;
    private Object object;

    public static RespBean ok(String msg){
        return new RespBean(200,msg,null);
    }

    public static RespBean ok(String msg,Object object) {
        return new RespBean(200, msg, object);
    }

    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }

    public static RespBean error(String msg, Object object) {
        return new RespBean(500, msg, object);
    }

    private RespBean(){

    }

    private RespBean(Integer status, String msg, Object object) {
        this.status = status;
        this.msg = msg;
        this.object = object;
    }
}
