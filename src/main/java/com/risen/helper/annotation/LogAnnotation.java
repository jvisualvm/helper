package com.risen.helper.annotation;

import java.lang.annotation.*;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 17:28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String menu();
    String module();
    String operateType();
}
