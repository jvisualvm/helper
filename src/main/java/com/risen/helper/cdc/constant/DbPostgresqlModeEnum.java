package com.risen.helper.cdc.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/14 14:03
 */
public enum DbPostgresqlModeEnum {


    INITIAL("initial"),
    INITIAL_ONLY("initial_only"),
    EXPORTED("exported"),
    ALWAYS("always"),
    CUSTOM("custom"),
    NEVER("never");


    @Getter
    @Setter
    private String code;


    DbPostgresqlModeEnum(String code) {
        this.code = code;
    }


}
