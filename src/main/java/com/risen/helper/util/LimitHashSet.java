package com.risen.helper.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/22 14:57
 */
public class LimitHashSet<T> extends HashSet<T> implements Serializable {

    private Integer limitLength;
    //缺省值
    private T defaultValue;

    public LimitHashSet(Integer limitLength, T defaultValue) {
        this.limitLength = limitLength;
        this.defaultValue = defaultValue;
    }

    public boolean addOne(T o) {
        if (this.size() >= limitLength) {
            return false;
        }
        this.add(o);
        return true;
    }


    public T getOne() {
        if (!this.isEmpty()) {
            Iterator<T> iterator = this.iterator();
            while (iterator.hasNext()) {
                return iterator.next();
            }
        }
        return defaultValue;
    }


    @Override
    public String toString() {
        return "LimitHashSet{" +
                "limitLength=" + limitLength +
                ", defaultValue=" + defaultValue +
                '}';
    }
}
