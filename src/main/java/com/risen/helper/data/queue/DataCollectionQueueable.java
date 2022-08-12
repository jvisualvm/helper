package com.risen.helper.data.queue;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.risen.helper.queue.AbstractQueue;
import com.risen.helper.thread.ThreadPoolUtil;
import com.risen.helper.util.LogUtil;
import com.risen.helper.util.PredicateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 11:41
 */
@Component
@Deprecated
public abstract class DataCollectionQueueable<T> extends AbstractQueue<List<T>> implements Serializable {

    @Autowired
    private ThreadPoolUtil threadPoolUtil;

    public List<T> resultList = new ArrayList<>();


    public abstract void removeExistData(List<T> resultList);

    public abstract void init();

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
        List<T> deviceAldResult = get();
        Optional.ofNullable(deviceAldResult).ifPresent(item -> {
            synchronized (resultList) {
                resultList.addAll(item);
            }
        });
    }

}
