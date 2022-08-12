package com.risen.helper.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/5 19:39
 */
@Component
@Data
public class FileConfig{

    @Value("${minio.endpoint:http://127.0.0.1}")
    private String endpoint;

    @Value("${minio.port:9000}")
    private Integer port;

    @Value("${minio.accessKey:admin}")
    private String accessKey;

    @Value("${minio.secretKey:admin123456}")
    private String secretKey;

    @Value("${minio.bucketName:bucket}")
    private String bucketName;

    @Value("${minio.auth:false}")
    private Boolean auth;
}
