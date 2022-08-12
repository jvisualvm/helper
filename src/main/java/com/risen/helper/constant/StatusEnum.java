package com.risen.helper.constant;

import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/15 17:04
 */
public enum StatusEnum {


    NORMAL(1, "正常"),
    ABNORMAL(0, "异常");

    @Getter
    @Setter
    private Integer code;
    @Getter
    @Setter
    private String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public EnumMap getEumMap() {
        return new EnumMap(StatusEnum.class);
    }

    public EnumSet getEumSet() {
        return EnumSet.allOf(StatusEnum.class);
    }
}
