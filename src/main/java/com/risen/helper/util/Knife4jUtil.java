package com.risen.helper.util;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/7/22 11:12
 */
public class Knife4jUtil {

    public static Docket createRestApi(String groupName, String basePackage, String description, String title, String version) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo(description, title, version))
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();

    }

    private static ApiInfo apiInfo(String description, String title, String version) {
        return new ApiInfoBuilder()
                .description(description)
                .version(version)
                .title(title)
                .build();
    }


}
