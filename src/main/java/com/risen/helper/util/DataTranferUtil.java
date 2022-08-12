package com.risen.helper.util;

import org.apache.commons.lang3.ObjectUtils;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/12 15:20
 */
public class DataTranferUtil {
    /**
     * 存在bug，部分类型不能强转
     *
     * @param t
     * @param u
     * @param defaultValue
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T, U> U tansferTo(T t, Class<U> u, Object defaultValue) {
        if (ObjectUtils.isEmpty(t)) {
            return (U) defaultValue;
        }
        if (Integer.class.isAssignableFrom(u)) {
            return (U) Integer.valueOf(t.toString());
        }
        if (Float.class.isAssignableFrom(u)) {
            return (U) Float.valueOf(t.toString());
        }
        if (Long.class.isAssignableFrom(u)) {
            return (U) Long.valueOf(t.toString());
        }
        if (Double.class.isAssignableFrom(u)) {
            return (U) Double.valueOf(t.toString());
        }
        if (String.class.isAssignableFrom(u)) {
            return (U) t.toString();
        }
        return (U) defaultValue;
    }

    public static <T, U> U tansferToWitchTryCatch(T t, Class<U> u, Object defaultValue) {
        try {
            return tansferTo(t, u, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
