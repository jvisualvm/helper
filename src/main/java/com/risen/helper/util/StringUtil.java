package com.risen.helper.util;

import org.apache.commons.lang3.ObjectUtils;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/1 14:35
 */
public class StringUtil {

    public static void clear(StringBuilder builder) {
        builder.delete(0, builder.length());
    }


    public static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }


    public static String parseStr(Object obj) {
        return ObjectUtils.isEmpty(obj) ? null : String.valueOf(obj);
    }
}
