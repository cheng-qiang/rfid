package org.chen.rfid.service.exception;

import org.chen.rfid.model.CodeMsg;

/**
 * @author 程强
 * @date 2020年03月22日 9:47
 * @Description:
 */
public class GlobalException extends RuntimeException {

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}