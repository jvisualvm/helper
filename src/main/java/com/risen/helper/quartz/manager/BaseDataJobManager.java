package com.risen.helper.quartz.manager;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.UnableToInterruptJobException;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/2 11:13
 */
public abstract class BaseDataJobManager implements InterruptableJob {

    protected boolean stopTask = false;

    private static ConcurrentHashMap<String, BaseDataJobManager> implTree = new ConcurrentHashMap<>();

    public BaseDataJobManager() {
        implTree.put(taskKey(), this);
    }

    public abstract String taskKey();

    public abstract String cron();


    public abstract boolean isSystem();

    public abstract boolean runWithServiceStart();

    @Override
    public abstract void execute(JobExecutionContext jobExecutionContext);


    public static BaseDataJobManager getImplTree(String taskKey) {
        return implTree.get(taskKey);
    }

    public static List<BaseDataJobManager> getImplTree() {
        return implTree.values().stream().collect(Collectors.toList());
    }


    @Override
    public void interrupt() throws UnableToInterruptJobException {
        stopTask = true;
    }

}
