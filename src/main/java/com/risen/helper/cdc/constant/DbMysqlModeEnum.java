package com.risen.helper.cdc.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/14 14:03
 */
public enum DbMysqlModeEnum {


    INITIAL("initial"),
    EARLIEST("earliest"),
    LATEST("latest"),
    SPECIFIC("specific"),
    TIMESTAMP("timestamp");


    @Getter
    @Setter
    private String code;


    DbMysqlModeEnum(String code) {
        this.code = code;
    }


}
