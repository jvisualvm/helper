package com.risen.helper.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/11 14:51
 */
@Data
public class DingDingResponse implements Serializable {

    /**
     * {
     * "status": 1111,
     * "wait": 5,
     * "source": "x5",
     * "punish": "deny",
     * "uuid": "3a074a612cad752a64a0e341acc4a53e",
     * "errcode": 130101,
     * "errmsg": "send too fast, exceed 20 times per minute"
     * }
     */

    private Integer status;
    private Integer wait;
    private String source;
    private String punish;
    private String uuid;
    private Integer errcode;
    private String errmsg;


}
