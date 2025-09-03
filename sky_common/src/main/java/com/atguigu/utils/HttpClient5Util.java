package com.atguigu.utils;

import com.alibaba.fastjson2.JSONObject;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient 5.x 工具类
 * 
 * 主要变化：
 * 1. 包名从 org.apache.http.* 变更为 org.apache.hc.client5.*
 * 2. 超时配置使用 Timeout 类而不是 int
 * 3. 更好的资源管理和异常处理
 */
public class HttpClient5Util {

    // 超时时间配置
    private static final Timeout TIMEOUT = Timeout.ofSeconds(5);

    /**
     * 发送GET方式请求
     * @param url 请求URL
     * @param paramMap 请求参数
     * @return 响应结果
     */
    public static String doGet(String url, Map<String, String> paramMap) {
        // 使用 try-with-resources 自动管理资源
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            
            // 构建URI
            URIBuilder builder = new URIBuilder(url);
            if (paramMap != null) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            URI uri = builder.build();

            // 创建GET请求
            HttpGet httpGet = new HttpGet(uri);
            
            // 设置请求配置
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(TIMEOUT)
                    .setConnectionRequestTimeout(TIMEOUT)
                    .setResponseTimeout(TIMEOUT)
                    .build();
            httpGet.setConfig(config);

            // 发送请求并处理响应
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                // 判断响应状态
                if (response.getCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        return EntityUtils.toString(entity, "UTF-8");
                    }
                }
            }
        } catch (IOException | URISyntaxException | ParseException e) {
            e.printStackTrace();
        }
        
        return "";
    }

    /**
     * 发送POST方式请求（表单数据）
     * @param url 请求URL
     * @param paramMap 请求参数
     * @return 响应结果
     */
    public static String doPost(String url, Map<String, String> paramMap) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            
            // 创建POST请求
            HttpPost httpPost = new HttpPost(url);
            
            // 设置请求配置
            httpPost.setConfig(buildRequestConfig());

            // 创建参数列表
            if (paramMap != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> param : paramMap.entrySet()) {
                    paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                // 设置表单实体
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }

            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        
        return "";
    }

    /**
     * 发送POST方式请求（JSON数据）
     * @param url 请求URL
     * @param paramMap 请求参数
     * @return 响应结果
     */
    public static String doPost4Json(String url, Map<String, String> paramMap) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            
            // 创建POST请求
            HttpPost httpPost = new HttpPost(url);
            
            // 设置请求配置
            httpPost.setConfig(buildRequestConfig());

            if (paramMap != null) {
                // 构造JSON格式数据
                JSONObject jsonObject = new JSONObject();
                for (Map.Entry<String, String> param : paramMap.entrySet()) {
                    jsonObject.put(param.getKey(), param.getValue());
                }
                
                // 创建JSON实体
                StringEntity entity = new StringEntity(
                    jsonObject.toString(), 
                    ContentType.APPLICATION_JSON
                );
                httpPost.setEntity(entity);
            }

            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        
        return "";
    }

    /**
     * 构建请求配置
     * @return RequestConfig
     */
    private static RequestConfig buildRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setResponseTimeout(TIMEOUT)
                .build();
    }
}
