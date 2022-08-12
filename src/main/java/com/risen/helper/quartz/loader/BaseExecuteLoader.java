package com.risen.helper.quartz.loader;

import com.risen.helper.cache.SystemMainCache;
import com.risen.helper.consumer.ConditionConsumer;
import com.risen.helper.quartz.base.BaseTaskLoader;
import com.risen.helper.quartz.manager.BaseDataJobManager;
import com.risen.helper.util.IfUtil;
import com.risen.helper.util.PredicateUtil;
import com.risen.helper.util.ThreadLocalUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/2 11:38
 */
@Component
@Order(value = 7)
@AllArgsConstructor
public class BaseExecuteLoader extends BaseTaskLoader implements CommandLineRunner {

    private SystemMainCache systemMainCache;

    @Override
    public void run(String... args) throws Exception {
        List<BaseDataJobManager> registerServiceList = BaseDataJobManager.getImplTree();
        ConditionConsumer ifConsumer = () -> {
            ThreadLocalUtil.inLocal.set(systemMainCache);
            registerServiceList.forEach(item -> {
                addAndStartTask(item, () -> {
                    return item.runWithServiceStart() || item.isSystem();
                });
            });
        };
        IfUtil.goIf(ifConsumer, PredicateUtil.isNotEmpty(registerServiceList));
    }


}
