package com.risen.helper.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.risen.helper.response.Result;
import com.risen.helper.response.ResultProxy;
import com.risen.helper.util.LogUtil;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 15:21
 */
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public Object exceptionHandler(BusinessException businessException) {
        LogUtil.error("\nerrorCode:{}\nerrorMsg:{}",
                businessException.getErrorCode(),
                businessException.getErrorMsg());
        return ResultProxy.build().failWithMsg(businessException.getErrorMsg(), businessException.getErrorCode());
    }

    // 参数校验异常异常处理
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result<String> handlerConstraintViolationException(Exception e) {
        ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
        String msg = StringUtils.collectionToCommaDelimitedString(
                constraintViolationException.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList()));
        e.printStackTrace();
        return ResultProxy.build().failWithMsg(msg);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<String> handlerMethodArgumentNotValidException(Exception e) {
        StringBuilder message = new StringBuilder();
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        for (ObjectError objectError : errors) {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                message.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(",");
            } else {
                message.append(objectError.getDefaultMessage()).append(",");
            }
        }
        e.printStackTrace();
        return ResultProxy.build().failWithMsg(message.toString());
    }

    @ExceptionHandler(value = BindException.class)
    public Result<String> handlerBindException(Exception e) {
        BindException bindException = (BindException) e;
        TreeMap<Object, Object> treeMap = new TreeMap<>();
        bindException.getBindingResult().getAllErrors().stream().forEach(item -> {
            treeMap.put(JSONObject.parseObject(JSON.toJSONString(item.getArguments()[0])).get("code"), item.getDefaultMessage());
        });
        e.printStackTrace();
        return ResultProxy.build().failWithObj(treeMap);
    }


    @ExceptionHandler(value = TooManyResultsException.class)
    public Result<String> TooManyResultsException(Exception e) {
        e.printStackTrace();
        return ResultProxy.build().failWithMsg("SQL查询存在问题:" + e.getMessage());
    }

    @ExceptionHandler(value = UnexpectedTypeException.class)
    public Result<String> unexpectedTypeException(Exception e) {
        e.printStackTrace();
        return ResultProxy.build().failWithMsg("参数不正确:" + e.getMessage());
    }


    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result<String> handlerMissingServletRequestParameterException(Exception e) {
        e.printStackTrace();
        return ResultProxy.build().failWithMsg("缺少必填参数:" + e.getMessage());
    }


    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result<String> handlerHttpMessageNotReadableException(Exception e) {
        e.printStackTrace();
        return ResultProxy.build().failWithMsg("参数没有序列化:" + e.getMessage());
    }


    @ExceptionHandler(value = NullPointerException.class)
    public Result<String> nullPointerException(Exception e) {
        e.printStackTrace();
        return ResultProxy.build().failWithMsg("空指针异常，请联系管理员:" + e.getMessage());
    }

    @ExceptionHandler(value = CannotGetJdbcConnectionException.class)
    public Result<String> cannotGetJdbcConnectionException(Exception e) {
        e.printStackTrace();
        return ResultProxy.build().failWithMsg("jdbc连接池异常，请联系管理员:" + e.getMessage());
    }


}
