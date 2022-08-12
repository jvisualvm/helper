package com.risen.helper.consumer;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/28 10:51
 */
@FunctionalInterface
public interface BiReturnConsumer<T, V> {

    V accept(T t);

}
