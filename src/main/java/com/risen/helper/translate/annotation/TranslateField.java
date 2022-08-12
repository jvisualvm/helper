package com.risen.helper.translate.annotation;

import java.lang.annotation.*;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 17:28
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TranslateField {

    String typeCode();

    String itemField();

}
