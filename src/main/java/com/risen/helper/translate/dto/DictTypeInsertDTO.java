package com.risen.helper.translate.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/15 19:21
 */
@Data
public class DictTypeInsertDTO {

    @NotNull
    private Integer id;

    private String typeCode;

    private String typeName;

    private String typeDesc;

    private String typeExt1;

    private Date updateTime;

    private Date createTime;

}
