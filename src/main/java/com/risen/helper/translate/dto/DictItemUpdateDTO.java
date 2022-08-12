package com.risen.helper.translate.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class DictItemUpdateDTO {

    @NotNull
    private Integer id;

    private String itemCode;

    private String itemName;

    private String itemDesc;

    private String itemNameEn;

    private String itemNameExt1;

    private String itemNameExt2;

    private String itemNameExt3;

    private String itemNameExt4;

    private String itemNameExt5;

    private String itemExtend1;

    private String itemExtend2;

    private String itemExtend3;

    private String itemExtend4;

    private String typeCode;

    private Integer typeId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField
    private Date updateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField
    private Date createTime;
}
