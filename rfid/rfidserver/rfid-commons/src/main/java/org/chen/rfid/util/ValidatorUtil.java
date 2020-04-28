package org.chen.rfid.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 程强
 * @date 2020年03月22日 9:32
 * @Description:
 */
public class ValidatorUtil {
    /**IP地址正则表达式*/
    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}");

    /**手机号正则表达式*/
    private static final Pattern PHONE_PATTERN = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$");

    /**身份证号正则表达式*/
    private static final Pattern IDENTITY_NUMBER_PATTERN = Pattern.compile("(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)");

    /**
     * 身份证号码校验
     * @param value
     * @return
     */
    public static boolean isIdentityNumber(String value) {
        if (StringUtils.isEmpty(value)){
            return false;
        }
        Matcher matcher = IDENTITY_NUMBER_PATTERN.matcher(value);
        return matcher.matches();
    }
    /**
     * 手机号码校验
     * @param value
     * @return
     */
    public static boolean isPhone(String value){
        if (StringUtils.isEmpty(value)){
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(value);
        return matcher.matches();
    }

    /**
     * ip地址校验
     * @param value
     * @return
     */
    public static boolean isIpAddress(String value) {
        if (StringUtils.isEmpty(value)){
            return false;
        }
        Matcher matcher = IP_ADDRESS_PATTERN.matcher(value);
        return matcher.matches();
    }

    public static void main(String[] args) {
        /*System.out.println(isIpAddress("1"));
        System.out.println(isIpAddress("10.0.0.0"));*/
        /*System.out.println(isPhone("3"));*/
        /*System.out.println(isIdentityNumber("410001910101123"));
        System.out.println(isIdentityNumber("41000119910101123X"));*/
    }


}
