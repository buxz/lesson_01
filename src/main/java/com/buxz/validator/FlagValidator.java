package com.buxz.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by SQ_BXZ on 2018-12-17.
 *  自定义验证的注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = FlagValidatorClass.class )
public @interface FlagValidator {
    // flag的有效值，多个使用 “，”隔开
    String values();

    // 提示内容
    String message() default "flag不存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
