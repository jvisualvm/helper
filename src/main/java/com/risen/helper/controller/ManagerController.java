package com.risen.helper.controller;

import com.risen.helper.log.dto.LogTemplateDTO;
import com.risen.helper.log.service.LogService;
import com.risen.helper.response.Result;
import com.risen.helper.response.ResultProxy;
import com.risen.helper.service.ManagerService;
import com.risen.helper.thread.ThreadPoolView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manager")
@AllArgsConstructor
@Api(tags = "系统管理接口")
/**
 * @author: zhangxin
 * @description: 简单的监控页面，监控日志/线程等指标 其他服务可以直接使用此接口 不需要自己开发实现
 * @date: 2022/3/7 13:37
 * @return
 */
public class ManagerController {

    private LogService logService;

    private ManagerService managerService;

    @GetMapping("/thread/view")
    @ApiOperation("查看线程池执行情况")
    public Result uploadImage() {
        return ResultProxy.build().successWithBody(ThreadPoolView.getThreadPoolRunStatusView());
    }


    @GetMapping("/log/page")
    @ApiOperation("查看系统操作日志")
    public Result uploadImage(LogTemplateDTO logTemplate) {
        return ResultProxy.build().successWithBody(logService.getLogPage(logTemplate));
    }

    @GetMapping("/list/system/detail")
    @ApiOperation("查看linux系统信息和jvm使用情况")
    public Result getSystemAndJvmDetail() {
        return ResultProxy.build().successWithBody(managerService.listMemoryStatus());
    }


}
