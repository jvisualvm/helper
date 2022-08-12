package com.risen.helper.translate.dto;

import com.risen.helper.base.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/15 19:20
 */
@Data
public class DictItemQueryDTO extends BasePage {

    @ApiModelProperty(name = "itemCode", value = "字典项code", example = "DICT_BUS_STATUS")
    private String itemCode;

    @ApiModelProperty(name = "itemName", value = "字典项名称", example = "轴状态")
    private String itemName;

    @ApiModelProperty(name = "typeId", value = "字典类id", example = "DICT_BUS_STATUS")
    private Integer typeId;

    @ApiModelProperty(name = "typeCode", value = "字典类code", example = "DICT_TREE")
    private String typeCode;
}
