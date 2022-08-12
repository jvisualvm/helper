package com.risen.helper.cdc.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/9 9:38
 */
public enum DbOperateEnum {

    CREATE("create", "创建/新增"),
    DELETE("delete", "删除"),
    UPDATE("update", "更新"),
    READ("read", "读取");


    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String msg;

    DbOperateEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
