package com.risen.helper.translate.dto;

import com.risen.helper.translate.entity.DictItemEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/16 15:12
 */
@Data
public class DictItemValueDTO {

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

    private Integer typeId;

    private String typeCode;


    public DictItemValueDTO(DictItemEntity item) {
        BeanUtils.copyProperties(item, this);
    }
}
