package org.chen.rfid.model;

/**
 * @author 程强
 * @date 2020年03月22日 9:42
 * @Description:
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data) {
        this.code =0 ;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm){
        if (cm == null){
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    /**
     * 成功时候调用
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 失败时候调用
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm){
        return new Result<T>(cm);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
