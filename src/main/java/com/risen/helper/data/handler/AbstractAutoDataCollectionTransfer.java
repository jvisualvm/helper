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

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 10:31
 */
@Component
public abstract class AbstractAutoDataCollectionTransfer<T, U, S extends ServiceImpl> extends AbstractQueue<List<U>> implements Serializable {

    protected Class<S> service;

    public AbstractAutoDataCollectionTransfer() {
        service = (Class<S>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        LogUtil.debug("AbstractAutoDataCollectionTransfer:{}", service.getName());
        startThread(SpringBeanUtil.getBean(service));
    }


    @Autowired
    private ThreadPoolUtil threadPoolUtil;

    protected List<U> resultList = new ArrayList<>();


    //数据清洗
    public abstract List<T> dataClear(List<T> source);

    //数据填充
    public abstract List<U> dataFill(List<T> source);

    //填充后的数据再次清洗
    public abstract List<U> dataClearAfterFill(List<U> source);

    //数据入库之前的去重方法
    public abstract void removeExistData(List<U> resultList);

    //数据入库
    private void dataInsert(List<U> result) {
        put(result);
    }

    public void processor(List<T> source) {
        if (PredicateUtil.isNotEmpty(source)) {
            Integer total = source.size();
            List<T> sourceTempList = new ArrayList<>(source);
            List<T> clearList = dataClear(source);
            source.removeAll(clearList);
            LogUtil.debug("dataClear collection throw away data[数据清洗->丢弃数据量:{},余下数据量:{},数据总量:{}],丢弃数据详情:{}", total - clearList.size(), clearList.size(), total, clearList.size() == 0 ? JSON.toJSONString(sourceTempList) : JSON.toJSONString(source));
            if (PredicateUtil.isNotEmpty(clearList)) {
                //数据填充
                List<U> resultFill = dataFill(clearList);
                Integer totalResult = resultFill.size();
                List<U> resultFillTempList = new ArrayList<>(resultFill);
                //继续清洗
                List<U> resultClear = dataClearAfterFill(resultFill);
                resultFill.removeAll(resultClear);
                LogUtil.debug("dataClearAfterFill collection throw away data[数据清洗->丢弃数据量:{},余下数据量:{},数据总量:{}],丢弃数据详情:{}", totalResult - resultClear.size(), resultClear.size(), totalResult, resultClear.size() == 0 ? JSON.toJSONString(resultFillTempList) : JSON.toJSONString(resultFill));
                //数据入库
                if (PredicateUtil.isNotEmpty(resultClear)) {
                    dataInsert(resultClear);
                }
            }
        }
    }


    private void startThread(ServiceImpl service) {
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
        List<U> deviceAldResult = get();
        Optional.ofNullable(deviceAldResult).ifPresent(item -> {
            synchronized (resultList) {
                resultList.addAll(item);
            }
        });
    }

}
