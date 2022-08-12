package com.risen.helper.translate.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/15 19:20
 */
@Data
public class DictItemInsertDTO {

    @NotNull
    @ApiModelProperty(name = "itemCode", value = "字典项code", example = "DICT_BUS_STATUS")
    private String itemCode;

    @ApiModelProperty(name = "itemName", value = "字典项中文名称", example = "轴状态")
    private String itemName;

    @ApiModelProperty(name = "itemDesc", value = "字典项描述", example = "用于测试")
    private String itemDesc;

    @ApiModelProperty(name = "itemNameEn", value = "字典项名称英文", example = "business")
    private String itemNameEn;


    @ApiModelProperty(name = "itemNameExt1", value = "字典项名称扩展字段，适配语种", example = "用于测试")
    private String itemNameExt1;

    @ApiModelProperty(name = "itemNameExt2", value = "字典项名称扩展字段，适配语种", example = "用于测试")
    private String itemNameExt2;

    @ApiModelProperty(name = "itemNameExt3", value = "字典项名称扩展字段，适配语种", example = "用于测试")
    private String itemNameExt3;

    @ApiModelProperty(name = "itemNameExt4", value = "字典项名称扩展字段，适配语种", example = "用于测试")
    private String itemNameExt4;

    @ApiModelProperty(name = "itemNameExt5", value = "字典项名称扩展字段，适配语种", example = "用于测试")
    private String itemNameExt5;

    @ApiModelProperty(name = "itemExtend1", value = "字典项扩展字段", example = "用于测试")
    private String itemExtend1;

    @ApiModelProperty(name = "itemExtend2", value = "字典项扩展字段", example = "用于测试")
    private String itemExtend2;

    @ApiModelProperty(name = "itemExtend3", value = "字典项扩展字段", example = "用于测试")
    private String itemExtend3;

    @ApiModelProperty(name = "itemExtend4", value = "字典项扩展字段", example = "用于测试")
    private String itemExtend4;

    @ApiModelProperty(name = "typeCode", value = "字典类code", example = "ASDA")
    @NotNull
    private String typeCode;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField
    private Date updateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField
    private Date createTime;
}
