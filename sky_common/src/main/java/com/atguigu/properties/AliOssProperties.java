package com.atguigu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用于配置阿里云OSS（对象存储服务）的相关参数
 * 主要用途：文件上传，文件下载，文件存储管理...
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOssProperties {

    // 阿里云OSS的访问域名（OSS的接入点地址）
    private String endpoint;

    // 阿里云OSS的访问密钥ID
    private String accessKeyId;

    // 阿里云OSS的访问密钥密码
    private String accessKeySecret;

    // 阿里云OSS的存储空间名称
    private String bucketName;
}
