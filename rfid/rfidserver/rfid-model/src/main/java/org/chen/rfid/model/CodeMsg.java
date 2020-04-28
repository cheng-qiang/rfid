package org.chen.rfid.model;

/**
 * @author 程强
 * @date 2020年03月22日 9:43
 * @Description:
 */
public class CodeMsg {
    private int code;
    private String msg;

    private CodeMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    /**带有参数的CodeMsg*/
    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code, message);
    }
    /**通用的错误码*/
    public static CodeMsg ERROR_SERVER = new CodeMsg(500100, "服务端异常");
    /**参数校验异常:%s 百分号s在字符串格式化的时候表示拼接字符串
     * JAVA字符串格式化-String.format()的使用：https://blog.csdn.net/lonely_fireworks/article/details/7962171
     * */
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");

    /**登录模块5002xx*/
    public static CodeMsg VERIFICATION_CODE_ERROR = new CodeMsg(500201, "验证码错误");
    /**定位基站模块 5003XX*/
    public static CodeMsg CODE_ERROR = new CodeMsg(500300, "编号已经存在，请过滤掉此编号");
    /**定位标签模块 5004XX*/

    /**访客信息模块 5005XX*/

    /**人员绑定模块 5006XX*/




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
}
