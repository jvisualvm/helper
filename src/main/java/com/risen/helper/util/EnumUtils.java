package com.risen.helper.util;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/24 14:01
 */
public class EnumUtils {

    public static Object[] getEnumArray(Class cls) {
        Object[] emptyArrays = new Object[]{};
        if (ObjectUtils.isEmpty(cls) || cls.getSimpleName().equals(Enum.class.getSimpleName())) {
            return emptyArrays;
        }
        if (!Enum.class.isAssignableFrom(cls)) {
            return emptyArrays;
        }
        Object[] objArray = cls.getEnumConstants();
        Object[] result = Stream.of(objArray).map(item -> {
            Field m;
            Object obj = null;
            try {
                m = item.getClass().getDeclaredField("code");
                m.setAccessible(true);
                obj = m.get(item);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                LogUtil.error("getEnumArray field error:{}", e.getMessage());
                e.printStackTrace();
            }
            return obj;
        }).toArray();
        return ObjectUtils.isNotEmpty(result) ? result : emptyArrays;
    }


}
