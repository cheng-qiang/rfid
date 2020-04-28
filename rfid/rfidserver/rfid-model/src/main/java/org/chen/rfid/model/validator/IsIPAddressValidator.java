package org.chen.rfid.model.validator;

import org.apache.commons.lang3.StringUtils;
import org.chen.rfid.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 程强
 * @date 2020年03月22日 9:29
 * @Description:
 * 自定义参数校验器需要实现ConstraintValidator<目标类型,校验属性类型>接口
 */
public class IsIPAddressValidator implements ConstraintValidator<IsIPAddress,String> {

    boolean required = false;

    @Override
    public void initialize(IsIPAddress constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return ValidatorUtil.isIpAddress(value);
        }else {
            if (StringUtils.isEmpty(value)){
                return true;
            }else {
                return ValidatorUtil.isIpAddress(value);
            }
        }
    }
}
