package com.risen.helper.data.scheduler;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import com.risen.helper.data.handler.DataTransfer;
import com.risen.helper.page.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 9:43
 */
@Deprecated
@Component
public abstract class AbstractScheduler<T> implements Serializable {

    public DataTransfer dataTransfer;

    public volatile AtomicReference<Boolean> FINISH_FLAG = new AtomicReference(false);

    @Autowired
    private PageUtil pageUtil;

    public AbstractScheduler(DataTransfer dataTransfer) {
        this.dataTransfer = dataTransfer;
    }

    public abstract void scheduler();

    public void dataHandler(Integer pageSize, BaseMapper mapper, Wrapper<T> queryWrapper) {
        FINISH_FLAG.set(false);
        Integer pageIndex = 1;
        pageSize = ObjectUtils.isEmpty(pageSize) ? 2000 : pageSize;
        boolean flag = true;
        Predicate<List> predicate = s -> CollectionUtils.isEmpty(s);
        while (flag) {
            PageInfo<T> pageInfo = pageUtil.page(pageIndex, pageSize, mapper, queryWrapper);
            if (predicate.test(pageInfo.getList())) {
                flag = false;
                break;
            }
            if (!CollectionUtils.isEmpty(pageInfo.getList())) {
                pageInfo.getList().forEach(item -> {
                    //执行处理操作
                    processor(item);
                });
            }
            ++pageIndex;
        }
        FINISH_FLAG.set(true);
    }

    public void processor(T t) {
        dataTransfer.processor(t);
    }

}
