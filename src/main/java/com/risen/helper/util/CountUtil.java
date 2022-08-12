package com.risen.helper.util;


import com.risen.helper.consumer.ForConsumer;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/19 9:49
 */
public class CountUtil {

    private static final Integer DEAULT_INTGER = 0;

    /**
     * 求N个数的平均值
     *
     * @param u
     * @param n
     * @param <U>
     * @return
     */
    public static <U extends Number> U avg(Class<U> u, U... n) {
        if (PredicateUtil.isAnyEmpty(u, n)) {
            return null;
        }
        return NumberUtil.divide(sum(u, n), n.length, u, DEAULT_INTGER);
    }


    /**
     * 求N的数的总和
     *
     * @param u
     * @param n
     * @param <U>
     * @return
     */
    public static <U extends Number> U sum(Class<U> u, U... n) {
        if (PredicateUtil.isAnyEmpty(u, n)) {
            return null;
        }
        if (Integer.class.isAssignableFrom(u)) {
            AtomicReference<Integer> count = new AtomicReference(DEAULT_INTGER);
            ForConsumer<U> forConsumer = (t) -> {
                if (PredicateUtil.isNotEmpty(t)) {
                    count.set(count.get() + (Integer) t);
                }
            };
            ForUtil.foreach(forConsumer, n);
            return (U) count.get();
        } else if (Float.class.isAssignableFrom(u)) {
            AtomicReference<Float> count = new AtomicReference(DEAULT_INTGER);
            ForConsumer<U> forConsumer = (t) -> {
                if (PredicateUtil.isNotEmpty(t)) {
                    count.set(count.get() + (Float) t);
                }
            };
            ForUtil.foreach(forConsumer, n);
            return (U) count.get();
        } else if (Double.class.isAssignableFrom(u)) {
            AtomicReference<Double> count = new AtomicReference(DEAULT_INTGER);
            ForConsumer<U> forConsumer = (t) -> {
                if (PredicateUtil.isNotEmpty(t)) {
                    count.set(count.get() + (Double) t);
                }
            };
            ForUtil.foreach(forConsumer, n);
            return (U) count.get();
        } else if (Long.class.isAssignableFrom(u)) {
            AtomicReference<Long> count = new AtomicReference(DEAULT_INTGER);
            ForConsumer<U> forConsumer = (t) -> {
                if (PredicateUtil.isNotEmpty(t)) {
                    count.set(count.get() + (Long) t);
                }
            };
            ForUtil.foreach(forConsumer, n);
            return (U) count.get();
        }
        return (U) DEAULT_INTGER;
    }


}
