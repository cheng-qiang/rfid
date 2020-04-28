package org.chen.rfid.service.exception;

import org.chen.rfid.model.CodeMsg;
import org.chen.rfid.model.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 程强
 * @date 2020年01月16日 17:20
 * @Description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        if (e instanceof GlobalException){
            GlobalException Gx = (GlobalException)e;
            return Result.error(Gx.getCm());
        }else if (e instanceof BindException){
            BindException bx = (BindException)e;
            List<ObjectError> errors = bx.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else{
            return Result.error(CodeMsg.ERROR_SERVER);
        }
    }
}
