package com.risen.helper.util;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.risen.helper.constant.Symbol;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.Collections;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/22 9:09
 */
public class MybatisPlusGeneratorUtil {
    public static String BASE_PATH = System.getProperty("user.dir") + "/generator";
    public static String PATH = BASE_PATH + "/code";
    public static String ZIP_FILENAME = "code.zip";
    public static String ZIP_FILEPATH;
    public static String ZIP_FINAL_FILEPATH;


    static {
        // /generator/zip/code.zip
        StringBuilder builder = new StringBuilder(BASE_PATH);
        builder.append(Symbol.SYMBOL_SLASH);
        builder.append("zip");
        ZIP_FILEPATH = builder.toString();

        StringBuilder finalZipName = new StringBuilder(ZIP_FILEPATH);
        finalZipName.append(Symbol.SYMBOL_SLASH);
        finalZipName.append(ZIP_FILENAME);
        ZIP_FINAL_FILEPATH = finalZipName.toString();
    }

    public static void create(String url, String userName, String password, String schema, String table, String tablePrefix, String projectPath, String packageName, NamingStrategy naming, String author) {
        File file = new File(projectPath);
        if (!file.exists()) {
            file.mkdir();
        }
        if (ObjectUtils.isEmpty(projectPath)) {
            projectPath = PATH;
        }
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(url, userName, password);
        dataSourceConfigBuilder.schema(schema);
        String finalProjectPath = projectPath;
        FastAutoGenerator.create(dataSourceConfigBuilder)
                // 全局配置 GlobalConfig
                .globalConfig(builder -> {
                    builder.author(author)
                            .fileOverride()
                            .enableSwagger()
                            .disableOpenDir()
                            .dateType(DateType.ONLY_DATE)
                            .outputDir(finalProjectPath);
                })
                // 包配置 PackageConfig
                .packageConfig(builder -> {
                    if (StringUtils.isEmpty(packageName)) {
                        builder.parent(packageName);
                    } else {
                        builder.parent("com.code");
                    }
                    builder.entity("entity")
                            .service("service")
                            .controller("controller")
                            .serviceImpl("serviceImpl")
                            .mapper("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, finalProjectPath + "/xml"));
                })
                // 配置策略 StrategyConfig
                .strategyConfig(builder -> {
                    if (!ObjectUtils.isEmpty(tablePrefix)) {
                        builder.addTablePrefix(tablePrefix);// 增加过滤表前缀，生成时将数据库表的前缀"p_"去掉
                    }
                    if (table.contains(Symbol.SYMBOL_COMMA)) {
                        builder.addInclude(table.split(Symbol.SYMBOL_COMMA));
                    } else {
                        builder.addInclude(table);
                    }
                    builder.entityBuilder()
                            .naming(naming)
                            .enableLombok()
                            .formatFileName("%sEntity")
                            .enableTableFieldAnnotation()
                            .enableChainModel()
                            //.idType(IdType.ASSIGN_ID)                   // 全局主键类型，配置后会在对应字段上生成 @TableId(value = "id", type = IdType.ASSIGN_ID)
                            // 2.service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            // 3.controller策略配置
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle()
                            // 4.mapper策略配置
                            .mapperBuilder()
                            .superClass(BaseMapper.class)
                            .enableMapperAnnotation()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper");
                })
                .execute();
    }


}
