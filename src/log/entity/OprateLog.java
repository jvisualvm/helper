package com.risen.resource.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.risen.helper.log.LogTemplate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 17:08
 */
@Data
@TableName(value = "operate_log")
@Component
public class QeCodeLogEntity extends LogTemplate {

    @TableId
    private Long operateId;

    @TableField
    private String menu;

    @TableField
    private String module;

    @TableField
    private String url;

    @TableField
    private String method;

    @TableField
    private String ip;

    @TableField
    private String param;

    @TableField
    private String result;

    @TableField
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    @TableField
    private String userName;

    @TableField
    private String operateType;


    @TableField
    private String osName;

    @TableField
    private String deviceType;

    @TableField
    private String browser;

    @TableField
    private String client;

    @TableField
    private String netType;

    @TableField
    private Integer status;

    @TableField
    private String errorMsg;


}

