package com.risen.helper.consumer;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/1 14:16
 */
@FunctionalInterface
public interface BiFiveConsumer<I, E, P, O, M> {

    void accept(I i, E e, P p, O o, M m);

}
