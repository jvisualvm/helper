package com.risen.helper.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.util.ObjectUtils;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/4 12:07
 */
public class JSONUtil {

    public static <T> String toSnakeCase(Object obj, Class<T> targetCls) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        Object object;
        ParserConfig parserConfig = new ParserConfig();
        parserConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        try {
            object = JSON.parseObject(obj.toString(), targetCls, parserConfig);
        } catch (Exception e) {
            LogUtil.error("toSnakeCase error:{}",e.getMessage());
            object = JSON.parseObject(JSON.toJSONString(obj), targetCls, parserConfig);
        }
        return JSON.toJSONString(object);
    }

}
