package com.risen.helper.generator.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/22 13:19
 */
@Data
public class GeneratorCodeRequestDTO {

    @ApiModelProperty(name = "url", value = "数据库连接地址", example = "jdbc:postgresql://1.1.1.1:5432/user")
    private String url;

    @ApiModelProperty(name = "userName", value = "用户名", example = "zhangxin")
    private String userName;

    @ApiModelProperty(name = "password", value = "密码", example = "root")
    private String password;

    @ApiModelProperty(name = "schema", value = "表模式（postgresql）", example = "s_td")
    private String schema;

    @ApiModelProperty(name = "table", value = "表名称", example = "user")
    private String table;

    @ApiModelProperty(name = "tablePrefix", value = "生成的实体类去掉的表前缀(可为空）", example = "device_")
    private String tablePrefix;

    @ApiModelProperty(name = "packageName", value = "包名称", example = "com.risen")
    private String packageName;

    @ApiModelProperty(name = "naming", value = "是否需要转成驼峰", example = "true")
    private Boolean naming=true;

    @ApiModelProperty(name = "author", value = "作者", example = "zhangxin")
    private String author;

}
