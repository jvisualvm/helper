package com.risen.helper.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/24 13:11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = MultiValidator.class)
public @interface MultiValid {

    Class enumCls() default Enum.class;

    String[] value() default {};

    String message() default "参数校验不通过，请重新输入";

    String fieldName();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
