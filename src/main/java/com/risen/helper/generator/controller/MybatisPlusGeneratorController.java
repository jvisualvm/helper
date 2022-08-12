package com.risen.helper.generator.controller;

import com.risen.helper.generator.dto.GeneratorCodeRequestDTO;
import com.risen.helper.generator.service.MybatisPlusGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/22 13:17
 */
@RestController
@RequestMapping(value = "/generator")
@AllArgsConstructor
@Api(tags = "Mybatis-plus代码自动生成")
public class MybatisPlusGeneratorController {

    private MybatisPlusGeneratorService mybatisPlusGeneratorService;

    @GetMapping(value = "/create")
    @ApiOperation(tags = "Mybatis-plus代码自动生成", value = "Mybatis-plus代码自动生成")
    public void createCode(GeneratorCodeRequestDTO generatorCodeRequestDTO, HttpServletResponse response) {
        mybatisPlusGeneratorService.createCode(generatorCodeRequestDTO, response);
    }


}
