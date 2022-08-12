package com.risen.helper.query.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/22 17:19
 */
@Data
public class BaseTimeQueryEntity {

    @TableField
    private Integer id;

    @TableField
    private Date evtTime;

    @TableField
    private Date createTime;

    @TableField
    private Date updateTime;

}
