package com.risen.helper.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/7 17:40
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class ManageKnife4jConfig {

    @Bean
    public Docket createManagerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统管理接口")
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.risen.helper.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    @Bean
    public Docket createCodeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("自动生成代码")
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.risen.helper.generator.controller"))
                .paths(PathSelectors.any())
                .build();

    }


    @Bean
    public Docket createDictManagerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("字典管理接口")
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.risen.helper.translate.controller"))
                .paths(PathSelectors.any())
                .build();

    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("系统管理接口")
                .contact(new Contact("", "", ""))
                .version("v1.1.0")
                .title("API接口文档")
                .build();
    }

}
