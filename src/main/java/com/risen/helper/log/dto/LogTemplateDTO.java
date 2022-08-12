package com.risen.helper.log.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 17:02
 */
@Data
@Component
@ApiModel("日志查询参数")
public class LogTemplateDTO {


    @ApiModelProperty("页码")
    private Integer pageIndex = 1;


    @ApiModelProperty("每页多少条数据")
    private Integer pageSize = 10;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("日志开始时间")
    private Date operateStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("日志结束时间")
    private Date operateEndTime;

    @ApiModelProperty("菜单")
    protected String menu;

    @ApiModelProperty("模块")
    protected String module;

    @ApiModelProperty("接口地址")
    protected String url;

    @ApiModelProperty("方法")
    protected String method;

    @ApiModelProperty("IP地址")
    protected String ip;

    @ApiModelProperty("参数")
    protected String param;

    @ApiModelProperty("返回结果")
    protected String result;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("操作时间 可选")
    protected Date operateTime;

    @ApiModelProperty("用户名称")
    protected String userName;

    @ApiModelProperty("客户端信息")
    protected String client;

    @ApiModelProperty("操作类型 insert,update,delete")
    protected String operateType;

    @ApiModelProperty("状态：正常1 异常0")
    private Integer status;

    @ApiModelProperty("错误信息")
    private String errorMsg;
}
