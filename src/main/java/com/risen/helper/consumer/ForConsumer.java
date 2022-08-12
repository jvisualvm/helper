package com.risen.helper.consumer;

import java.util.Objects;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/18 9:57
 */
@FunctionalInterface
public interface ForConsumer<T> {

    void accept(T t);

    default ForConsumer<T> andThen(ForConsumer<T> after) {
        Objects.requireNonNull(after);
        return (x) -> {
            accept(x);
            after.accept(x);
        };
    }


}
