package com.risen.helper.util;

import com.risen.helper.consumer.ForConsumer;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/18 9:54
 */
public class ForUtil {

    public static <T> void foreach(List<T> lst, ForConsumer<List<T>> consumer) {
        if (!CollectionUtils.isEmpty(lst)) {
            consumer.accept(lst);
        }
    }

    public static  <U extends Object> void foreach(ForConsumer<U> consumer, U[] o) {
        if (PredicateUtil.isAnyEmpty(consumer, o)) {
            return;
        }
        Stream.of(o).forEach(item -> {
            consumer.accept(item);
        });
    }

}
