package com.risen.helper.queue;

import com.risen.helper.log.entity.LogTemplate;
import com.risen.helper.log.source.LogSource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 18:03
 */
@Component
@AllArgsConstructor
public class RequestLogHandler extends AbstractQueue<LogTemplate> {

    private LogSource logSource;

    @Override
    public void exec() {
        LogTemplate logObj = get();
        if (ObjectUtils.isEmpty(logObj)) {
            return;
        }
        BeanUtils.copyProperties(logObj, logSource.getLogTemplate());
        logSource.getBaseMapper().insert(logSource.getLogTemplate());
    }
}
