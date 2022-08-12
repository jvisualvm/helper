package com.risen.helper.annotation;

import com.risen.helper.init.BeanInitProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/4 10:08
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(BeanInitProcessor.class)
public @interface EnableOrderClient {
}