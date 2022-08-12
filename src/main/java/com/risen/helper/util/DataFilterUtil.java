package com.risen.helper.util;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 15:15
 */
public class DataFilterUtil {

    public static boolean isNotEmpty(String... param) {
        AtomicReference<Integer> result = new AtomicReference(0);
        Integer size = ObjectUtils.isEmpty(param) ? 0 : param.length;
        Optional.ofNullable(param).ifPresent(item -> {
            Stream.of(item).forEach(ops -> {
                if (PredicateUtil.isNotEmpty(ops)) {
                    result.getAndSet(result.get() + 1);
                }
            });

        });
        return result.get().equals(size);
    }


}
