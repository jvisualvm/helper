package com.risen.helper.config;

import com.risen.helper.util.LogUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/7 13:51
 */
@Component
@Data
public class SwitchConfig {

    //日志打印功能开关
    @Value("${helper.log.switch:false}")
    private Boolean logOpenWitch;

    //文件上传下载功能开关
    @Value("${helper.file.switch:false}")
    private Boolean fileOpenWitch;

    //flyway功能开关
    @Value("${helper.flyway.switch:false}")
    private Boolean flywaySwitch;

    //定时删除日志开关
    @Value("${helper.clean.switch:false}")
    private Boolean cleanSwitch;

    //缓存开关
    @Value("${helper.cache.switch:false}")
    private Boolean cacheSwitch;

    //字段翻译开关
    @Value("${helper.translate.switch:false}")
    private Boolean translateSwitch;

    //websocket开关
    @Value("${helper.websocket.switch:false}")
    private Boolean websocketSwitch;

    //flink开关
    @Value("${helper.flink.switch:false}")
    private Boolean flinkSwitch;

    @Override
    public String toString() {
        return "SwitchConfig{" +
                "logOpenWitch=" + logOpenWitch +
                ", fileOpenWitch=" + fileOpenWitch +
                ", flywaySwitch=" + flywaySwitch +
                ", cleanSwitch=" + cleanSwitch +
                ", cacheSwitch=" + cacheSwitch +
                ", translateSwitch=" + translateSwitch +
                ", websocketSwitch=" + websocketSwitch +
                ", flinkSwitch=" + flinkSwitch +
                '}';
    }

    @PostConstruct
    public void printSwitchConfig() {
        LogUtil.info("helper function open config list:{}", toString());
    }

}
