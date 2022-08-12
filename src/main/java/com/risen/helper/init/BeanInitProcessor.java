package com.risen.helper.init;

import com.risen.helper.cdc.common.PortSetUtil;
import com.risen.helper.cdc.property.FlinkCDCBaseProperties;
import com.risen.helper.config.SwitchConfig;
import com.risen.helper.constant.Symbol;
import com.risen.helper.thread.ThreadPoolUtil;
import com.risen.helper.util.ForeachUtil;
import com.risen.helper.util.SpringBeanUtil;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/4 9:51
 */
public class BeanInitProcessor implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
            throw new IllegalArgumentException(
                    "AutowiredAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
        }
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;

        //初始化工具类
        initFlinkAllImpl("com.risen.helper.util", SpringBeanUtil.class,true);
        initFlinkAllImpl("com.risen.helper.thread", ThreadPoolUtil.class,true);
        initFlinkAllImpl("com.risen.helper.config", SwitchConfig.class,true);

        SwitchConfig switchConfig=SpringBeanUtil.getBean(SwitchConfig.class);
        if(switchConfig.getFlinkSwitch()) {
            initFlinkAllImpl("com.risen.helper.cdc.common", PortSetUtil.class,true);
            //初始化flink所有实现类
            initFlinkAllImpl("com.risen.helper.cdc.property", FlinkCDCBaseProperties.class, false);
            initFlinkAllImpl("org.apache.flink.streaming.api.functions.source", SinkFunction.class, false);
        }
    }

    private void initFlinkAllImpl(String packagePath, Class clsImpl,Boolean containMyself) {
        packagePath = packagePath.replace(Symbol.SYMBOL_POINT, Symbol.SYMBOL_SLASH);
        //扫描到所有类并初始化
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        Set<BeanDefinition> components;
        try {
            provider.addIncludeFilter(new AssignableTypeFilter(clsImpl));
            components = provider.findCandidateComponents(packagePath);
            Consumer<BeanDefinition> consumer = k -> {
                try {
                    Class cls = Class.forName(k.getBeanClassName());
                    Optional.ofNullable(cls).ifPresent(son -> {
                        if (containMyself||(son.isAssignableFrom(clsImpl) && son != clsImpl) || (clsImpl.getName().contains("Util"))) {
                            this.beanFactory.getBean(cls);
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            };
            ForeachUtil.forEachOne(components, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
