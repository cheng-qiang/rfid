package org.chen.rfid.model.validator;

import org.apache.commons.lang3.StringUtils;
import org.chen.rfid.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 程强
 * @date 2020年03月25日 17:04
 * @Description:
 */
public class IsPhoneValidator implements ConstraintValidator<IsPhone,String> {

    boolean required = false;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
         if (required){
             return ValidatorUtil.isPhone(value);
         }else {
             if (StringUtils.isEmpty(value)){
                 return true;
             }else {
                 return ValidatorUtil.isPhone(value);
             }
         }
    }

    @Override
    public void initialize(IsPhone constraintAnnotation) {
        required = constraintAnnotation.required();
    }
}
