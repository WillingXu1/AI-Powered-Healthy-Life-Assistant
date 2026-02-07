package com.zxs.people.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.minio")
// 这个注解的意思是：从Spring Boot的配置文件（application.yml/application.properties）
// 中读取以 spring.minio 为前缀的配置项，并自动映射到当前类的属性上。
public class MinioProperties {
    private String accessKey;
    private String secretKey;
    private String url;
    private String bucketName;
}
