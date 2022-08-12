package com.risen.helper.data.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.risen.helper.queue.AbstractQueue;
import com.risen.helper.thread.ThreadPoolUtil;
import com.risen.helper.util.LogUtil;
import com.risen.helper.util.PredicateUtil;
import com.risen.helper.util.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 10:31
 */
@Component
public abstract class AbstractAutoDataTransfer<T, U, S extends ServiceImpl> extends AbstractQueue<U> implements Serializable {


    @Autowired
    private ThreadPoolUtil threadPoolUtil;

    public List<U> resultList = new ArrayList<>();

    protected Class<S> service;

    public AbstractAutoDataTransfer() {
        service = (Class<S>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        LogUtil.debug("AbstractAutoDataTransfer:{}", service.getName());
        startThread(SpringBeanUtil.getBean(service));
    }


    //数据清洗
    protected abstract boolean dataClear(T source);

    //数据填充
    protected abstract U dataFill(T source);

    //移除重复数据
    public abstract void removeExistData(List<U> resultList);


    //数据入库
    private void dataInsert(U result) {
        put(result);
    }


    public void processor(T source) {

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


    public void startThread(ServiceImpl service) {
        threadPoolUtil.addTask(() -> {
            while (true) {
                exec();
                synchronized (resultList) {
                    if (PredicateUtil.isNotEmpty(resultList) && PredicateUtil.isOverLimit(resultList, 2000)) {
                        removeExistData(resultList);
                        if (PredicateUtil.isNotEmpty(resultList)) {
                            service.saveBatch(resultList);
                            resultList.clear();
                        }
                    }
                    if (PredicateUtil.isNotEmpty(resultList) && PredicateUtil.isZero(size())) {
                        removeExistData(resultList);
                        if (PredicateUtil.isNotEmpty(resultList)) {
                            service.saveBatch(resultList);
                            LogUtil.info("scheduler finish flush resultList to db");
                            resultList.clear();
                        }
                    }
                }
            }
        });
    }


    @Override
    public void exec() {
        U deviceAldResult = get();
        Optional.ofNullable(deviceAldResult).ifPresent(item -> {
            synchronized (resultList) {
                resultList.add(item);
            }
        });
    }


}
