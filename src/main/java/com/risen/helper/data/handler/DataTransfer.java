package com.risen.helper.data.handler;

import com.alibaba.fastjson.JSON;
import com.risen.helper.util.LogUtil;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 10:31
 */
@Deprecated
public interface DataTransfer<T, U> extends Serializable {

    //数据清洗
    public  boolean dataClear(T source);

    //数据填充
    public  U dataFill(T source);

    //数据入库
    public  void dataInsert(U result);


    default void processor(T source) {

        Predicate<Boolean> predicate = s -> s;
        //数据清洗
        if (predicate.test(dataClear(source))) {
            //数据填充
            U result = dataFill(source);
            //数据入库
            dataInsert(result);
        } else {
            LogUtil.info("throw away data[数据清洗->丢弃数据]:{}", JSON.toJSONString(source));
        }
    }

}
