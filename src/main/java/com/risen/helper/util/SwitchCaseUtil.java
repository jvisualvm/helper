package com.risen.helper.util;

import com.risen.helper.consumer.ConditionConsumer;
import com.risen.helper.consumer.ConsumerMap;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/26 13:53
 */
public class SwitchCaseUtil {

    /**
     * @param obj
     * @param consumers
     * @return void
     * @author: zhangxin
     * @description
     * @date: 2022/5/26 14:06
     */
    public static <O extends Object> void execute(O obj, ConsumerMap consumers) {
        if (PredicateUtil.isAnyEmpty(obj, consumers)) {
            LogUtil.error("param1 and param2 isAnyEmpty please check your param");
            return;
        }
        forEach(obj, consumers.consumerMap);
    }

    /**
     * @param obj
     * @param consumerMap
     * @return void
     * @author: zhangxin
     * @description: 可以跳出循环的foreach语句
     * @date: 2022/5/26 15:15
     */
    public static <O extends Object> void forEach(O obj, Map<Object, ConditionConsumer> consumerMap) {
        Predicate<Object> predicate = s -> obj.equals(DataTranferUtil.tansferTo(s, obj.getClass(), null));
        for (Map.Entry<Object, ConditionConsumer> entry : consumerMap.entrySet()) {
            ConditionConsumer v;
            try {
                v = entry.getValue();
                if (predicate.test(entry.getKey())) {
                    v.accept();
                    //匹配一次就跳出循环
                    break;
                }
            } catch (IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
        }
    }


}
