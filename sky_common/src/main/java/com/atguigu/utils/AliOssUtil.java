package com.atguigu.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    // 填写Bucket所在地域对应的Endpoint
    private String endpoint;

    // 阿里云账号的AccessKeyId
    private String accessKeyId;

    // 阿里云账号的AccessKeySecret
    private String accessKeySecret;

    // 填写Bucket名称
    private String bucketName;

    /**
     * 文件上传
     * @param bytes 要上传的文件字节数组
     * @param fileName 文件名
     */
    public String upload(byte[] bytes, String fileName) {

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try{
            // 创建PutObject请求，PutObject指的是上传一个文件（或流）到Bucket
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            // 输出OSS错误信息
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            // 输出Client错误信息
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(fileName);

        log.info("文件上传到:{}", stringBuilder.toString());

        return stringBuilder.toString();
    }
}
