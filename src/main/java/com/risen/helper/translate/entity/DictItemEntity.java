package com.risen.helper.translate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.risen.helper.translate.annotation.TranslateField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/15 19:20
 */
@TableName("dict_item")
@Data
public class DictItemEntity {

    @TableId
    private Integer id;

    @TableField
    private String itemCode;

    @TableField
    private String itemName;

    @TableField
    private String itemDesc;

    @TableField
    private String itemNameEn;

    @TableField
    private String itemNameExt1;

    @TableField
    private String itemNameExt2;

    @TableField
    private String itemNameExt3;

    @TableField
    private String itemNameExt4;

    @TableField
    private String itemNameExt5;

    @TableField
    private String itemExtend1;

    @TableField
    private String itemExtend2;

    @TableField
    private String itemExtend3;

    @TableField
    private String itemExtend4;

    @TableField
    private Integer typeId;

    @TableField
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
