package com.risen.helper.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/4 18:20
 */
public class DateFormatUtil {


    public static String formatDate(Date date, String patter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patter);
        return simpleDateFormat.format(date);
    }


}
