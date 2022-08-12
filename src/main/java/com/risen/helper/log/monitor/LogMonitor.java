package com.risen.helper.log.monitor;

import com.risen.helper.queue.RequestLogHandler;
import com.risen.helper.thread.ThreadPoolUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 18:57
 */
@Component
@AllArgsConstructor
public class LogMonitor{

    private RequestLogHandler requestLogHandler;
    private ThreadPoolUtil threadPoolUtil;

    @PostConstruct
    public void startLogThread() {
        threadPoolUtil.addTask(() -> {
            while (true) {
                requestLogHandler.exec();
            }
        });

    }


}
