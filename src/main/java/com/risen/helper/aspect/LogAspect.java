package com.risen.helper.aspect;

import com.risen.helper.config.SwitchConfig;
import com.risen.helper.constant.StatusEnum;
import com.risen.helper.constant.Symbol;
import com.risen.helper.log.entity.LogTemplate;
import com.risen.helper.queue.RequestLogHandler;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 16:59
 */
@Aspect
@Component
public class LogAspect {

    private RequestLogHandler requestLogHandler;
    private SwitchConfig switchConfig;


    public LogAspect(RequestLogHandler requestLogHandler, SwitchConfig switchConfig) {
        this.requestLogHandler = requestLogHandler;
        this.switchConfig = switchConfig;
    }

    @Pointcut("@annotation(com.risen.helper.annotation.LogAnnotation)")
    private void pointcut() {

    }

    @Around("pointcut()")
    public Object logAroundInterceptor(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Object[] argObject = point.getArgs();
        Set<Object> argsObjectHasByFilter = argsFilter(argObject);
        if (switchConfig.getLogOpenWitch()) {
            LogTemplate logTemplate = new LogTemplate();
            logTemplate.initVisitInfo(method, argsObjectHasByFilter, result);
            logTemplate.setStatus(StatusEnum.NORMAL.getCode());
            requestLogHandler.put(logTemplate);
        }
        return result;
    }

    @AfterThrowing(throwing = "ex", pointcut = "pointcut()")
    public void logAfterInterceptor(JoinPoint point, Throwable ex) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Object[] argObject = point.getArgs();
        Set<Object> argsObjectHasByFilter = argsFilter(argObject);
        if (switchConfig.getLogOpenWitch()) {
            LogTemplate logTemplate = new LogTemplate();
            logTemplate.initVisitInfo(method, argsObjectHasByFilter, null);
            logTemplate.setStatus(StatusEnum.ABNORMAL.getCode());
            StringBuilder errorLogBuilder = new StringBuilder();
            errorLogBuilder.append(ex);
            errorLogBuilder.append(Symbol.SYMBOL_SLASH);
            errorLogBuilder.append(ex.getStackTrace()[0].toString());
            logTemplate.setErrorMsg(errorLogBuilder.toString());
            requestLogHandler.put(logTemplate);
        }
    }


    private Set<Object> argsFilter(Object[] argObject) {
        if (ObjectUtils.isEmpty(argObject)) {
            return new HashSet<>();
        }
        Set<Object> argsObjectHasByFilter = new HashSet<>();
        Stream.of(argObject).forEach(item -> {
            if (item instanceof MultipartFile) {

            } else {
                argsObjectHasByFilter.add(item);
            }
        });
        return argsObjectHasByFilter;
    }


}
