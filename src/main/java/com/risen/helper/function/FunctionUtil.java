package com.risen.helper.function;

import com.risen.helper.util.DateCompareUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/23 18:08
 */
@Component
public class FunctionUtil {

    public <T extends Date, D extends Date> boolean cutNowYear(T t, D d) {
        BiFunction<Date, Date, Boolean> fi = (m, k) -> {
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(m);
            Calendar data = Calendar.getInstance();
            data.setTime(k);
            return (calendarNow.get(Calendar.YEAR) == data.get(Calendar.YEAR));
        };
        return fi.apply(t, d);
    }


    public <T extends Date, D extends Date> boolean cutNowMonth(T t, D d) {
        BiFunction<Date, Date, Boolean> fi = (m, k) -> {
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(m);
            Calendar data = Calendar.getInstance();
            data.setTime(k);
            return (calendarNow.get(Calendar.YEAR) == data.get(Calendar.YEAR)) && calendarNow.get(Calendar.MONTH) == data.get(Calendar.MONTH);
        };
        return fi.apply(t, d);
    }


    public <T extends Date, D extends Date> boolean cutNowDay(T t, D d) {
        BiFunction<Date, Date, Boolean> fi = (m, k) -> {
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(m);
            Calendar data = Calendar.getInstance();
            data.setTime(k);
            return (calendarNow.get(Calendar.YEAR) == data.get(Calendar.YEAR)) &&
                    (calendarNow.get(Calendar.MONTH) == data.get(Calendar.MONTH)) &&
                    (calendarNow.get(Calendar.DAY_OF_YEAR) == data.get(Calendar.DAY_OF_YEAR));
        };
        return fi.apply(t, d);
    }


    public <T> Boolean filterNow(T beanBody, String filterColumn, Date beanDate, Boolean needFilterYear, Boolean needFilterMonth, Boolean needFilterDay) throws NoSuchFieldException, IllegalAccessException {
        ListFilterNowFunction<T, String, Date, Boolean, Boolean, Boolean> function = (obj, column, objDate, yy, mm, dd) -> {
            Field fieldTime = obj.getClass().getDeclaredField(column);
            fieldTime.setAccessible(true);

            Date fieldTimeValue = (Date) fieldTime.get(obj);

            Calendar objTime = Calendar.getInstance();
            objTime.setTime(fieldTimeValue);

            Calendar currentTime = Calendar.getInstance();
            currentTime.setTime(new Date());
            Predicate<Boolean> s = x -> false;
            if (s.test(yy)) {
                return objTime.get(Calendar.YEAR) != currentTime.get(Calendar.YEAR);
            }
            if (s.test(mm)) {
                return objTime.get(Calendar.MONTH) != currentTime.get(Calendar.MONTH);
            }
            if (s.test(dd)) {
                return objTime.get(Calendar.DAY_OF_YEAR) != currentTime.get(Calendar.DAY_OF_YEAR);
            }
            return DateCompareUtil.compareAfter(fieldTimeValue, objDate);
        };
        return function.isFilter(beanBody, filterColumn, beanDate, needFilterYear, needFilterMonth, needFilterDay);
    }


}
