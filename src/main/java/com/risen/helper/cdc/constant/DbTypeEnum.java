package com.risen.helper.cdc.constant;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/9 17:22
 */
public enum DbTypeEnum {

    MYSQL("mysql"),
    POSTGRESQL("postgresql");

    @Getter
    @Setter
    private String code;

    DbTypeEnum(String code) {
        this.code = code;
    }


    public static Map<String, DbTypeEnum> getEnumMap() {
        return Stream.of(values()).collect(Collectors.toMap(DbTypeEnum::getCode, s -> s));
    }


}
