package com.risen.helper.log.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.risen.helper.annotation.LogAnnotation;
import com.risen.helper.util.IpAddressUtil;
import com.risen.helper.util.UserClientUtil;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 17:02
 */
@Data
@Component
public class LogTemplate {


    private Long operateId;

    private String menu;

    private String module;

    private String url;

    private String method;

    private String ip;

    private String param;

    private String result;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    private String userName;

    private String operateType;


    private String osName;
    private String deviceType;
    private String browser;
    private String client;
    private String netType;

    private Integer status;
    private String errorMsg;


    public void initVisitInfo(Method method, Set<Object> argsObjectHasByFilter, Object result) {

        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);

        this.setMenu(logAnnotation.menu());
        this.setModule(logAnnotation.module());
        this.setOperateType(logAnnotation.operateType());
        this.setOperateTime(new Date());

        this.setMethod(method.getName());
        this.setParam(JSON.toJSONString(argsObjectHasByFilter));
        this.setUserName(null);
        this.setResult(JSON.toJSONString(result));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress = IpAddressUtil.getIpAddress(request);
        this.setIp(ipAddress);
        this.setNetType(IpAddressUtil.getNetWorkType(ipAddress));
        this.setUrl(request.getRequestURI());
        UserClientUtil.ClientDetail clientDetail = UserClientUtil.getUserClintDetail(request);
        if (ObjectUtils.isNotEmpty(clientDetail)) {
            this.setClient(clientDetail.getClient());
            this.setOsName(clientDetail.getOsName());
            this.setBrowser(clientDetail.getBrowser());
            this.setDeviceType(clientDetail.getDeviceType());
        }
    }

}
