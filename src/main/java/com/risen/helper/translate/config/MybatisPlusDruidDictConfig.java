package com.risen.helper.translate.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/12 11:09
 * @Description 多数据源情况下如果不配置字典库，默认使用的master库
 */
@ConditionalOnProperty(prefix = "spring.datasource", name = "dict.url")
@Configuration
@MapperScan(basePackages = "com.risen.helper.translate.mapper", sqlSessionTemplateRef = "dict")
public class MybatisPlusDruidDictConfig {


    /**********************************************************dict配置************************************************/


    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean("dictDruidDataSource")
    public DruidDataSource deviceDataSource() {
        return new DruidDataSource();
    }


    @ConfigurationProperties(prefix = "spring.datasource.dict")
    @Bean("dictDataSourceProperties")
    public DataSourceProperties dictDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean(name = "dictDataSource")
    public DataSource dictDataSource(@Qualifier("dictDruidDataSource") DruidDataSource dataSource, @Qualifier("dictDataSourceProperties") DataSourceProperties dataSourceProperties) {
        BeanUtils.copyProperties(dataSourceProperties, dataSource);
        return dataSource;
    }

    /**********************************************************dict配置************************************************/


    /**********************************************************dict配置************************************************/

    @Bean("dictSqlSessionFactory")
    public SqlSessionFactory dictSqlSessionFactory(@Qualifier("dictDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath*:mapper/dict/*Mapper.xml"));
        SqlSessionFactory sqlSession = sqlSessionFactory.getObject();
        sqlSession.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSession;
    }

    @Bean(name = "dictTransactionManager")
    public DataSourceTransactionManager dictTransactionManager(@Qualifier("dictDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean(name = "dict")
    public SqlSessionTemplate dictSqlSessionTemplate(@Qualifier("dictSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**********************************************************dict配置************************************************/


}




