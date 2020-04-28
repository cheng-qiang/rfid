package org.chen.rfid.service.exception.custom;

/**
 * @author 程强
 * @date 2020年01月17日 11:16
 * @Description:RFID 系统内部异常
 */
public class RfidException extends Exception{

    private static final long serialVersionUID = -994962710559017255L;

    public RfidException(String message){
        super(message);
    }
}
