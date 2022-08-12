package com.risen.helper.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/4 11:31
 */
@Data
public class BasePage {
    /**
     * 当前页码
     */
    @ApiModelProperty(name = "pageIndex", value = "当前页码", example = "1")
    private int pageIndex = 1;
    /**
     * 每页数量
     */
    @ApiModelProperty(name = "pageSize", value = "每页数量", example = "10")
    private int pageSize = 10;
}
