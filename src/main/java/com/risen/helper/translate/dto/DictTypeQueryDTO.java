package com.risen.helper.translate.dto;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.risen.helper.base.BasePage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/15 19:21
 */
@Data
public class DictTypeQueryDTO extends BasePage {

    private String typeCode;

    private String typeName;
}
