package com.risen.helper.runner;

import com.risen.helper.cache.*;
import com.risen.helper.thread.ThreadPoolUtil;
import com.risen.helper.util.ForeachUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/30 10:22
 */
@Component
@Order(value = 1)
@AllArgsConstructor
public class DataCacheLoader implements CommandLineRunner {

    private ThreadPoolUtil threadPoolUtil;

    @Override
    public void run(String... args) throws Exception {
        //服务启动的时候加载全部缓存
            Consumer<AgentCacheAbstract> consumerAgentCacheAbstract = t -> {
                t.loadCache();
            };
            Consumer<CacheCommonAbstract> consumerCacheCommonAbstract = t -> {
                t.loadAll();
            };
            Consumer<CacheDataAbstract> consumerCacheDataAbstract = t -> {
                t.loadCache();
            };
            Consumer<PermanentCacheAbstract> permanentCacheAbstractConsumer = t -> {
                t.loadCache();
            };
            Consumer<CacheMinuteDataAbstract> cacheMinuteDataAbstractConsumer = t -> {
                t.loadCache();
            };
            ForeachUtil.forEachOne(AgentCacheAbstract.getImplTree(), consumerAgentCacheAbstract);
            ForeachUtil.forEachOne(CacheCommonAbstract.getImplTree(), consumerCacheCommonAbstract);
            ForeachUtil.forEachOne(CacheDataAbstract.getImplTree(), consumerCacheDataAbstract);
            ForeachUtil.forEachOne(PermanentCacheAbstract.getImplTree(), permanentCacheAbstractConsumer);
            try {
                ForeachUtil.forEachOne(CacheMinuteDataAbstract.getImplTree(), cacheMinuteDataAbstractConsumer);
            }
            catch (Exception e){
                e.printStackTrace();
            }
    }
}
