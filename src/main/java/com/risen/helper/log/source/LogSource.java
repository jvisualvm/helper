package com.risen.helper.log.source;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.risen.helper.log.entity.LogTemplate;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/7 12:53
 */
@Component
@Data
public class LogSource {

    protected LogTemplate logTemplate;
    protected BaseMapper baseMapper;

    /**
     * @param logTemplate
     * @param baseMapper
     * @return void
     * @author: zhangxin
     * @description: 使用此jar包的服务，可以注入自己的日志mapper和entity 这样就不需要自己实现日志功能了
     * @date: 2022/3/7 13:49
     */
    public void initMapper(LogTemplate logTemplate, BaseMapper baseMapper) {
        setBaseMapper(baseMapper);
        setLogTemplate(logTemplate);
    }

}
