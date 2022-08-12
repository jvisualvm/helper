package com.risen.helper.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/27 11:58
 */
@Component
public class GenericParamUtil {


    public <T extends Object> Class<T> getFirstCls(T t) {
        return (Class<T>) ((ParameterizedType) t.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    public <T extends Object> Class<T> getSecondCls(T t) {
        return (Class<T>) ((ParameterizedType) t.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }


    public <T extends Object> Class<T> getThirdCls(T t) {
        return (Class<T>) ((ParameterizedType) t.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    }

    public <T extends Object> Class<T> getFourCls(T t) {
        return (Class<T>) ((ParameterizedType) t.getClass().getGenericSuperclass()).getActualTypeArguments()[3];
    }

    public <T extends Object> Class<T> getFiveCls(T t) {
        return (Class<T>) ((ParameterizedType) t.getClass().getGenericSuperclass()).getActualTypeArguments()[4];
    }


    public <T extends Object> Class<T> getCls(T t, Integer index) {
        return (Class<T>) ((ParameterizedType) t.getClass().getGenericSuperclass()).getActualTypeArguments()[--index];
    }


}




