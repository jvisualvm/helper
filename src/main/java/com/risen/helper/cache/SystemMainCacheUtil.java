package com.risen.helper.cache;

import com.risen.helper.util.ThreadLocalUtil;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/25 16:39
 */
public class SystemMainCacheUtil {


    public static <T extends Object> T getSystemObj(Class<T> cls) {
        SystemMainCache systemCache = (SystemMainCache) ThreadLocalUtil.inLocal.get();
        return (T) systemCache.get(cls.hashCode());
    }


}
