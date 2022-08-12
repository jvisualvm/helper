package com.risen.helper.cdc.common;

import lombok.Data;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/4 10:47
 */
@Data
@Component
@ConditionalOnProperty(prefix = "helper.flink", name = "switch",havingValue = "true")
public class PortSetUtil {

    @Value(value = "${rest.bind-port:59428}")
    private String port;

    public void setUiPort(StreamExecutionEnvironment env) {
        try {
            if (env instanceof LocalStreamEnvironment) {
                Field field = env.getClass().getSuperclass().getDeclaredField("configuration");
                field.setAccessible(true);
                Configuration configuration = (Configuration) field.get(env);
                configuration.setString("rest.bind-port", port);
            } else {
                //RemoteStreamEnvironment
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
