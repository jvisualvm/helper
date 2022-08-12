package com.risen.helper.data.scheduler;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.risen.helper.consumer.ForConsumer;
import com.risen.helper.data.handler.AbstractAutoDataTransfer;
import com.risen.helper.page.PageService;
import com.risen.helper.util.LogUtil;
import com.risen.helper.util.SpringBeanUtil;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 9:43
 */
@Component
public abstract class AbstractAutoScheduler<T, M extends AbstractAutoDataTransfer> implements Serializable {

    protected Class<M> dataTransfer;

    public AbstractAutoScheduler() {
        dataTransfer = (Class<M>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        LogUtil.debug("AbstractAutoScheduler dataTransfer:{}", dataTransfer.getName());
    }

    public volatile AtomicReference<Boolean> FINISH_FLAG = new AtomicReference(false);

    public abstract void scheduler();

    public void dataHandler(Integer pageSize, BaseMapper mapper, Wrapper<T> queryWrapper) {
        FINISH_FLAG.set(false);

        ForConsumer<List<T>> consumer = t -> {
            t.forEach(item -> {
                processor(item);
            });
        };
        PageService.processByPage(pageSize, mapper, queryWrapper, consumer);
        FINISH_FLAG.set(true);
    }

    private void processor(T t) {
        AbstractAutoDataTransfer abstractAutoDataTransfer = SpringBeanUtil.getBean(dataTransfer);
        abstractAutoDataTransfer.processor(t);
    }

}
