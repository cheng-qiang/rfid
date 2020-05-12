package org.chen.rfid.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 言少钱
 * @date 2020年05月05日 12:07
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：
 * @Description:
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface SysLog {
    String value() default "";
}
