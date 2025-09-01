package com.atguigu.test;

import com.google.gson.JsonObject;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class HttpClientTest {

    /**
     * 测试通过HttpClient发送GET方式的请求
     */
    @Test
    public void testGet() throws Exception{
        // 1.创建CloseableHttpClient对象（使用HttpClients.createDefault()）
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 2.创建HttpGet请求对象
        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");

        // 3.调用HttpClient的execute方法发送请求（并用CloseableHttpResponse接收结果）
        CloseableHttpResponse result = httpClient.execute(httpGet);

        // 4.获取服务器返回的状态码
        int statusCode = result.getCode();
        System.out.println("服务器返回的状态码为：" + statusCode);

        // 5.获取服务器返回的数据
        HttpEntity responseEntity = result.getEntity();
        String body = EntityUtils.toString(responseEntity);
        System.out.println("服务器返回的数据为：" + body);

        // 6.关闭资源
        result.close();
        httpClient.close();
    }

    @Test
    public void testPost() throws Exception{
        // 1.创建CloseableHttpClient对象（使用HttpClients.createDefault()）
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 2.创建HttpPost请求对象
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");

        JsonObject json = new JsonObject(); // 2-1. 因为是Json，所以需要导入Gson依赖并创建JsonObject对象
        json.addProperty("username", "admin");  // 2-2. 添加参数
        json.addProperty("password", "123456");
        StringEntity requestEntity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);   // 2-3. 创建StringEntity对象，并设置请求体
        httpPost.setEntity(requestEntity);  // 2-4. 设置请求体

        // 3.调用HttpClient的execute方法发送请求（并用CloseableHttpResponse接收结果）
        CloseableHttpResponse result = httpClient.execute(httpPost);

        // 4.获取服务器返回的状态码
        int statusCode = result.getCode();
        System.out.println("服务器返回的状态码为：" + statusCode);

        // 5.获取服务器返回的数据
        HttpEntity responseEntity = result.getEntity();
        String body = EntityUtils.toString(responseEntity);
        System.out.println("服务器返回的数据为：" + body);

        // 6.关闭资源
        result.close();
        httpClient.close();
    }
}
