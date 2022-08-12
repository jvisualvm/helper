package com.risen.helper.translate.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/15 20:44
 */
@Data
public class DictAllItemQueryDTO {

    @ApiModelProperty(name = "typeId", value = "字典类id",example = "1")
    private Integer typeId;

    @ApiModelProperty(name = "typeCode", value = "字典类code",example = "ASDA")
    private String typeCode;

}
