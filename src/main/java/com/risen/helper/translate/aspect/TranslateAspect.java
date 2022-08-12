package com.risen.helper.translate.aspect;

import com.risen.helper.config.SwitchConfig;
import com.risen.helper.translate.annotation.TranslateField;
import com.risen.helper.translate.cache.DictLoadCache;
import com.risen.helper.util.LogUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/6 16:59
 * @Description receive return object and Processor
 */
@Aspect
@Component
public class TranslateAspect {

    private SwitchConfig switchConfig;
    private DictLoadCache dictLoadCache;

    public TranslateAspect(DictLoadCache dictLoadCache, SwitchConfig switchConfig) {
        this.dictLoadCache = dictLoadCache;
        this.switchConfig = switchConfig;
    }

    @Pointcut("@within(com.risen.helper.translate.annotation.TranslateService)")
    private void pointcut() {

    }

    @Around("pointcut()")
    public Object translateAroundInterceptor(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        Predicate<Boolean> predicate = s -> s;
        if (predicate.test(switchConfig.getTranslateSwitch())) {
            Optional.ofNullable(result).ifPresent(item -> {
                returnObjectProcessor(item);
            });
        }
        return result;
    }

    private void returnObjectProcessor(Object obj) {
        if (!notEmpty(obj)) {
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        Field[] superFields = new Field[]{};
        Class superClass = obj.getClass().getSuperclass();
        if (notEmpty(superClass)) {
            superFields = superClass.getDeclaredFields();
        }
        Field[] allField = ArrayUtils.addAll(fields, superFields);
        Map<String, Field> fieldMap = Stream.of(allField).collect(Collectors.toMap(s -> s.getName(), s -> s));
        Stream.of(allField).forEach(field -> {
            field.setAccessible(true);
            Class fieldType = field.getType();
            try {
                Object fieldValue = field.get(obj);
                if (List.class.isAssignableFrom(fieldType)) {
                    //如果是list需要递归
                    if (notEmpty(fieldValue)) {
                        List<Object> fieldList = (List) fieldValue;
                        fieldList.forEach(item -> {
                            returnObjectProcessor(item);
                        });
                    }
                } else if (Set.class.isAssignableFrom(fieldType)) {
                    //如果是set需要递归
                    if (notEmpty(fieldValue)) {
                        Set<Object> fieldSet = (Set) fieldValue;
                        fieldSet.forEach(item -> {
                            returnObjectProcessor(item);
                        });
                    }
                } else if (String.class.isAssignableFrom(fieldType)) {
                    changeReturnValueOfString(field, obj, fieldMap);
                } else if (Object.class.isAssignableFrom(fieldType) && !Number.class.isAssignableFrom(fieldType)) {
                    returnObjectProcessor(field.get(obj));
                }
            } catch (IllegalAccessException e) {
                LogUtil.info("find field :{},error:{}", field.getName(), e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void changeReturnValueOfString(Field field, Object obj, Map<String, Field> fieldMap) {
        //可能是单个object, object里面又有list，list里面又有object，需要递归处理字段
        TranslateField translateField = field.getDeclaredAnnotation(TranslateField.class);
        if (notEmpty(translateField)) {
            String typeCode = translateField.typeCode();
            String itemField = translateField.itemField();
            Field fieldStr = fieldMap.get(itemField);
            if (notEmpty(typeCode) && notEmpty(itemField) && notEmpty(fieldStr)) {
                fieldStr.setAccessible(true);
                try {
                    Object value = fieldStr.get(obj);
                    if (notEmpty(value)) {
                        field.set(obj, dictLoadCache.getDictNameByCacheKey(typeCode, value));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    LogUtil.info("find {} error:{}", fieldStr.getName(), e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }


    private Boolean notEmpty(Object in) {
        Predicate<Object> predicate = s -> ObjectUtils.isNotEmpty(s);
        return predicate.test(in);
    }
}
