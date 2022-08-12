package com.risen.helper.consumer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/26 14:45
 */
public class ConsumerMap {

    public Map<Object, ConditionConsumer> consumerMap = new HashMap<>();

    private ConsumerMap() {

    }

    public static ConsumerMap build() {
        return new ConsumerMap();
    }

    public ConsumerMap add(Object obj, ConditionConsumer consumer) {
        consumerMap.put(obj, consumer);
        return this;
    }

}
