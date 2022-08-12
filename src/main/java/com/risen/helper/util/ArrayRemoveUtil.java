package com.risen.helper.util;

import com.risen.helper.consumer.BiReturnConsumer;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/28 10:33
 */
@Component
public class ArrayRemoveUtil {

    public <T extends Object> void remove(List<T> lst, BiReturnConsumer<T, Boolean> biConsumer) {
        if (PredicateUtil.isNotEmpty(lst)) {
            Iterator<T> iterator = lst.iterator();
            while (iterator.hasNext()) {
                if (biConsumer.accept(iterator.next())) {
                    iterator.remove();
                }
            }
        }
    }

}
