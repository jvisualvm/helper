package com.risen.helper.quartz.base;

import com.risen.helper.quartz.manager.BaseDataJobManager;
import com.risen.helper.quartz.manager.BaseJobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/20 15:38
 */
@Component
public abstract class BaseTaskLoader {

    @Autowired
    private BaseJobManager quartzManager;

    public void addAndStartTask(BaseDataJobManager item, Supplier<Boolean> isStart) {
        if (isStart.get()) {
            quartzManager.removeJob(item.taskKey());
            quartzManager.startJob(item.taskKey(), item.cron(), item.getClass());
        }
    }

}
