package org.chen.rfid.model.validator;

import org.apache.commons.lang3.StringUtils;
import org.chen.rfid.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 程强
 * @date 2020年03月25日 17:28
 * @Description:
 */
public class IsIdentityNumberValidator implements ConstraintValidator<IsIdentityNumber,String> {

    boolean required = false;

    @Override
    public void initialize(IsIdentityNumber constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
       if (required){
           return ValidatorUtil.isIdentityNumber(value);
       }else {
           if (StringUtils.isEmpty(value)){
               return true;
           }else {
               return ValidatorUtil.isIdentityNumber(value);
           }
       }
    }
}
