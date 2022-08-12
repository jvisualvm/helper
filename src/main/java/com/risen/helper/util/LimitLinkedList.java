package com.risen.helper.util;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/22 14:57
 */
public class LimitLinkedList<T> extends LinkedList<T> implements Serializable {

    private Integer limitLength;

    public LimitLinkedList(){}


    public LimitLinkedList(Integer limitLength) {
        if (limitLength <= 0) {
            throw new RuntimeException("队列限制数量不能<=0");
        }
        this.limitLength = limitLength;
    }

    public boolean addNotRemoveHead(T o) {
        if (this.size() >= limitLength) {
            return false;
        }
        this.add(o);
        return true;
    }


    public boolean addRemoveHead(T o) {
        if (this.size() >= limitLength) {
            add(o);
            if (!CollectionUtils.isEmpty(this)) {
                remove(0);
            }
            return true;
        }
        this.add(o);
        return true;
    }


    @Override
    public String toString() {
        return "LimitLinkedList{" +
                "limitLength=" + limitLength +
                '}';
    }
}
