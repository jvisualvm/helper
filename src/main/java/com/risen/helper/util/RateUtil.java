package com.risen.helper.util;

import com.risen.helper.constant.Symbol;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/27 13:11
 */
public class RateUtil {

    public static String rate(Float count) {
        if (ObjectUtils.isEmpty(count)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(count * 100);
        builder.append(Symbol.SYMBOL_BFH);
        return builder.toString();
    }

}
