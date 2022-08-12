package com.risen.helper.cache;

import com.risen.helper.http.HttpComponent;
import com.risen.helper.util.ForeachUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/25 16:34
 */
@Component
public class SystemMainCache extends PermanentCacheAbstract<Integer, Object> {

    @Override
    public void loadCache() {
        List<AgentCacheAbstract> agentCacheAbstractList = AgentCacheAbstract.getImplTree();
        Consumer<AgentCacheAbstract> agentCacheAbstractConsumer = (t) -> {
            this.put(t.getClass().hashCode(), t);
        };
        ForeachUtil.forEachOne(agentCacheAbstractList, agentCacheAbstractConsumer);

        List<CacheCommonAbstract> cacheCommonAbstractList = CacheCommonAbstract.getImplTree();
        Consumer<CacheCommonAbstract> cacheCommonAbstractConsumer = (t) -> {
            this.put(t.getClass().hashCode(), t);
        };
        ForeachUtil.forEachOne(cacheCommonAbstractList, cacheCommonAbstractConsumer);

        List<CacheDataAbstract> cacheDataAbstractList = CacheDataAbstract.getImplTree();
        Consumer<CacheDataAbstract> cacheDataAbstractConsumer = (t) -> {
            this.put(t.getClass().hashCode(), t);
        };
        ForeachUtil.forEachOne(cacheDataAbstractList, cacheDataAbstractConsumer);

        List<HttpComponent> httpComponentList = HttpComponent.getImplTree();
        Consumer<HttpComponent> httpComponentConsumer = (t) -> {
            this.put(t.getClass().hashCode(), t);
        };
        ForeachUtil.forEachOne(httpComponentList, httpComponentConsumer);


    }


}
