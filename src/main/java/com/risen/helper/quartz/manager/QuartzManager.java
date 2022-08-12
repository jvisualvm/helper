package com.risen.helper.quartz.manager;

import com.risen.helper.quartz.response.TaskDetail;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/15 11:54
 */
@Component
public class QuartzManager {

    @Autowired
    private SchedulerFactory schedulerFactory;

    public static final String JOB_GROUP_NAME = "manager_group";
    public static final String TRIGGER_GROUP_NAME = "manager_trigger";
    public static final String PARAM = "param";

    public void addCronJob(String jobName, Class<? extends Job> cls, String time, Map<String, Object> parameter) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();
            if (!CollectionUtils.isEmpty(parameter)) {
                jobDetail.getJobDataMap().put(PARAM, parameter);
            }
            scheduler.scheduleJob(jobDetail, getCronTriggerByJobName(jobName, time));
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addCronJob(String jobName, String jobGroupName,
                           String triggerName, String triggerGroupName, Class<? extends Job> jobClass,
                           String time, Map<String, Object> parameter) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            scheduler.scheduleJob(jobDetail, getCronTriggerByTriggerName(triggerName, triggerGroupName, time));
            if (CollectionUtils.isEmpty(parameter)) {
                jobDetail.getJobDataMap().put(PARAM, parameter);
            }
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void updateJob(String jobName, String time) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (ObjectUtils.isEmpty(trigger)) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                Class<? extends Job> objJobClass = jobDetail.getJobClass();
                removeJob(jobName);
                addCronJob(jobName, objJobClass, time, null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateJob(String triggerName, String triggerGroupName, String time) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (ObjectUtils.isEmpty(trigger)) {
                return;
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(trigger.getCronExpression());
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .startNow()
                        .withSchedule(scheduleBuilder)
                        .withSchedule(CronScheduleBuilder.cronSchedule(time))
                        .build();
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeJob(String jobName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
            JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdownJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CronTrigger getCronTriggerByJobName(String jobName, String time) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobName, TRIGGER_GROUP_NAME)
                .withSchedule(CronScheduleBuilder.cronSchedule(time))
                .startNow()
                .build();
    }

    public void pauseTask(String jobName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.pauseJob(JobKey.jobKey(jobName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resumeTask(String jobName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.resumeJob(JobKey.jobKey(jobName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Trigger getSimpleIntervalSecondTrigger(String jobName, Integer interval) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, TRIGGER_GROUP_NAME)
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                //每小时执行
                                .withIntervalInSeconds(interval)
                                .repeatForever()

                )
                .build();
        return trigger;
    }

    private Trigger getSimpleIntervalMinutesTrigger(String jobName, Integer interval) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, TRIGGER_GROUP_NAME)
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                //每分钟执行
                                .withIntervalInMinutes(interval)
                                .repeatForever()
                )
                .build();
        return trigger;
    }

    private Trigger getSimpleIntervalHoursTrigger(String jobName, Integer interval) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, TRIGGER_GROUP_NAME)
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                //每秒执行
                                .withIntervalInHours(interval)
                                .repeatForever()
                )
                .build();
        return trigger;
    }


    private CronTrigger getCronTriggerByTriggerName(String triggerName, String triggerGroupName, String time) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(time))
                .startNow()
                .build();
    }


    public List<TaskDetail> getJobList() {
        List<TaskDetail> taskDetailList = new ArrayList<>();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
            for (String groupName : triggerGroupNames) {
                GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
                Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
                fillResponseDetail(taskDetailList, triggerKeySet, groupName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return taskDetailList;
    }

    private void fillResponseDetail(List<TaskDetail> taskDetailList, Set<TriggerKey> triggerKeySet, String groupName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            for (TriggerKey triggerKey : triggerKeySet) {
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                JobKey jobKey = trigger.getJobKey();
                JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                TaskDetail taskDetailResponse =
                        TaskDetail.builder().
                                cron(trigger.getCronExpression()).
                                jobName(jobDetail.getName()).
                                jobGroupName(jobDetail.getGroup()).
                                triggerGroupName(groupName).build();
                taskDetailList.add(taskDetailResponse);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
