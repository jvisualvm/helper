package com.risen.helper.log.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.risen.helper.log.dto.LogTemplateDTO;
import com.risen.helper.log.entity.LogTemplate;
import com.risen.helper.log.source.LogSource;
import com.risen.helper.page.PageUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/7 12:47
 */
@Service
@AllArgsConstructor
public class LogService {
    private LogSource logSource;
    private PageUtil pageUtil;

    public PageInfo<LogTemplate> getLogPage(LogTemplateDTO logTemplate) {
        return pageUtil.page(logTemplate.getPageIndex(), logTemplate.getPageSize(), logSource.getBaseMapper(), updateQueryConditionByRequest(logTemplate));
    }


    private Wrapper updateQueryConditionByRequest(LogTemplateDTO logTemplate) {
        QueryWrapper<LogTemplate> wrapper = new QueryWrapper();
        wrapper.like(StringUtils.isNotEmpty(logTemplate.getClient()), "client", logTemplate.getClient());
        wrapper.like(StringUtils.isNotEmpty(logTemplate.getMenu()), "menu", logTemplate.getMenu());
        wrapper.like(StringUtils.isNotEmpty(logTemplate.getModule()), "module", logTemplate.getModule());
        wrapper.like(StringUtils.isNotEmpty(logTemplate.getParam()), "param", logTemplate.getParam());
        wrapper.like(StringUtils.isNotEmpty(logTemplate.getResult()), "result", logTemplate.getResult());
        wrapper.like(StringUtils.isNotEmpty(logTemplate.getUserName()), "user_name", logTemplate.getUserName());
        wrapper.like(StringUtils.isNotEmpty(logTemplate.getMethod()), "method", logTemplate.getMethod());

        wrapper.like(StringUtils.isNotEmpty(logTemplate.getErrorMsg()), "error_msg", logTemplate.getErrorMsg());

        wrapper.ge(ObjectUtils.isNotEmpty(logTemplate.getOperateStartTime()), "operate_time", logTemplate.getOperateStartTime());
        wrapper.le(ObjectUtils.isNotEmpty(logTemplate.getOperateEndTime()), "operate_time", logTemplate.getOperateEndTime());
        wrapper.eq(StringUtils.isNotEmpty(logTemplate.getOperateType()), "operate_type", logTemplate.getOperateType());

        wrapper.eq(ObjectUtils.isNotEmpty(logTemplate.getStatus()), "status", logTemplate.getStatus());
        wrapper.orderByAsc("operate_id");
        return wrapper;
    }

}
