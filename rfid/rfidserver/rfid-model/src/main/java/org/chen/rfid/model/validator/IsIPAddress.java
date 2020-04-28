package org.chen.rfid.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 程强
 * @date 2020年03月22日 9:28
 * @Description:
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsIPAddressValidator.class}
)
public @interface IsIPAddress {
    boolean required() default true;

    String message() default "IP地址格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
