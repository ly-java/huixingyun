package com.jbtx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author xiaoyou
 * @title: SmsConfig
 * @projectName financial
 * @description: oss config
 * @date 2019/6/1216:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss")
@PropertySource(value = "classpath:oss.properties")
public class OSSConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private String address;

}
