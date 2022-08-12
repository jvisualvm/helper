package com.risen.helper.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/3 15:33
 */
public enum DingDingMsgTypeEnum {

    TEXT("text"),
    MARKDOWN("markdown"),
    LINK("link"),
    FEEDCARD("feedCard"),
    ACTIONCARD("actionCard");


    @Setter
    @Getter
    private String type;

    DingDingMsgTypeEnum(String type) {
        this.type = type;
    }


}
