package com.risen.helper.constant;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/17 16:41
 */
public enum BoolEnum {


    F(0, false),

    T(1, true);

    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private Boolean bool;


    BoolEnum(Integer code, Boolean bool) {
        this.code = code;
        this.bool = bool;
    }


    public static Map<Boolean, Integer> getMap() {
        return Stream.of(values()).collect(Collectors.toMap(BoolEnum::getBool, BoolEnum::getCode));
    }


}
