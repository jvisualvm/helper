package com.risen.helper.queue;

import com.risen.helper.util.LogUtil;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 11:34
 */
public abstract class AbstractQueue<T> implements Serializable {

    public LinkedBlockingDeque<T> queue = new LinkedBlockingDeque<>(102400);

    public void put(T t) {
        queue.offer(t);
    }

    public abstract void exec();

    public T get() {
        T element = null;
        try {
            element = queue.take();
            if (ObjectUtils.isEmpty(element)) {
                return null;
            }
        } catch (InterruptedException e) {
            LogUtil.error("queue take fail:{}", e.toString());
        }
        return element;
    }


    public Integer size() {
        return queue.size();
    }


}
