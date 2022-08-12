package com.risen.helper.consumer;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/1 14:16
 */
@FunctionalInterface
public interface BiFourConsumer<I, E, P,O> {

    void accept(I i, E e, P p,O o);

}
