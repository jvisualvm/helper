package com.risen.helper.function;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/22 9:32
 */
@FunctionalInterface
public interface ListFilterNowFunction<T, C, I, Y, M, D> {

    Boolean isFilter(T obj, C filterColumn, I filterTime, Y currentYear, M currentMonth, D currentDay) throws NoSuchFieldException, IllegalAccessException;

}
