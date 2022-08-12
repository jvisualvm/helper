package com.risen.helper.validator;

import com.alibaba.fastjson.JSON;
import com.risen.helper.util.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/24 13:13
 */
public class MultiValidator implements ConstraintValidator<MultiValid, Object> {
    private Class cls;
    private Set<String> value;
    private String fieldName;

    @Override
    public void initialize(MultiValid multiValid) {
        this.value = Stream.of(multiValid.value()).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(value)) {
            this.value = new HashSet<>();
        }
        this.cls = multiValid.enumCls();
        this.fieldName = multiValid.fieldName();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(obj)) {
            return true;
        }
        if (ObjectUtils.isNotEmpty(cls)&&!cls.getSimpleName().equals(Enum.class.getSimpleName())) {
            return validEnumValue(obj, constraintValidatorContext);
        }
        if (!CollectionUtils.isEmpty(value)) {
            validStrArrayValue(obj, constraintValidatorContext);
        }
        return false;
    }

    private boolean validEnumValue(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        Object[] enumArray = EnumUtils.getEnumArray(cls);
        Set<String> enumSet = Stream.of(enumArray).map(s -> String.valueOf(s)).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(enumSet) && enumSet.contains(String.valueOf(obj))) {
            return true;
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(String.format("%s只能输入以下数组中的某一项值:%s", fieldName, JSON.toJSONString(enumArray))).addConstraintViolation();
        return false;
    }

    private boolean validStrArrayValue(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if (!CollectionUtils.isEmpty(value) && value.contains(String.valueOf(obj))) {
            return true;
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(String.format("%s不存在该状态值请检查", fieldName)).addConstraintViolation();
        return false;
    }


}
