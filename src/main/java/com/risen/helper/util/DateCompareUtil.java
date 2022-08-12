package com.risen.helper.util;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/18 17:01
 */
public class DateCompareUtil {

    public static boolean compareAfter(Date target, Date source) {

        if (ObjectUtils.isEmpty(target)) {
            return false;
        }
        if (ObjectUtils.isEmpty(source)) {
            return true;
        }

        return (source.getTime() == target.getTime()) || target.after(source);
    }

}
