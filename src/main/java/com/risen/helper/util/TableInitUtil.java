package com.risen.helper.util;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/27 12:05
 */
public class TableInitUtil {

    public static void initTable(Class cls) {
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), cls);
    }


    public static void initTable(Object obj, Integer index) {
        GenericParamUtil paramUtil = SpringBeanUtil.getBean(GenericParamUtil.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), paramUtil.getCls(obj, index));
    }

    public static void initTable(Object obj) {
        GenericParamUtil paramUtil = SpringBeanUtil.getBean(GenericParamUtil.class);
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), paramUtil.getCls(obj, 1));
    }

}
