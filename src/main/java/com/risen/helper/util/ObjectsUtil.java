package com.risen.helper.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/1 14:35
 */
public class ObjectsUtil {

    public static void clear(StringBuilder builder) {
        builder.delete(0, builder.length());
    }

    public static String isEmptyAndFill(String obj, String defaultValue) {
        return StringUtils.isNotEmpty(obj) ? obj : defaultValue;
    }

    public static Object isEmptyAndFill(Object obj, Object defaultValue) {
        return ObjectUtils.isNotEmpty(obj) ? obj : defaultValue;
    }


    public static Object findNotEmpty(Object... obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        Object isNull = null;
        for (Object i : obj) {
            if (ObjectUtils.isNotEmpty(i)) {
                isNull = i;
                break;
            }
        }
        return isNull;
    }


}
