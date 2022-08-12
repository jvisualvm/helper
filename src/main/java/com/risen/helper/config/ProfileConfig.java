package com.risen.helper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/14 14:46
 */
@Configuration
public class ProfileConfig {

    @Autowired
    private ApplicationContext context;

    public String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

}
