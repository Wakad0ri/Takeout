# HttpClient 4.5.x 到 5.x 迁移指南

## 📋 概述

本文档帮助您从 Apache HttpClient 4.5.x 迁移到 5.x 版本。

## 🔄 主要变化

### 1. Maven 依赖变化

#### 4.5.x 版本
```xml
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.13</version>
</dependency>
```

#### 5.x 版本
```xml
<dependency>
    <groupId>org.apache.httpcomponents.client5</groupId>
    <artifactId>httpclient5</artifactId>
    <version>5.4.4</version>
</dependency>
```

### 2. 包名变化

| 4.5.x 包名 | 5.x 包名 |
|------------|----------|
| `org.apache.http.client.methods.*` | `org.apache.hc.client5.http.classic.methods.*` |
| `org.apache.http.impl.client.*` | `org.apache.hc.client5.http.impl.classic.*` |
| `org.apache.http.client.config.*` | `org.apache.hc.client5.http.config.*` |
| `org.apache.http.util.*` | `org.apache.hc.core5.http.io.entity.*` |

### 3. Import 语句对比

#### 4.5.x Import
```java
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
```

#### 5.x Import
```java
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
```

## 🔧 代码迁移示例

### GET 请求对比

#### 4.5.x 版本
```java
CloseableHttpClient httpClient = HttpClients.createDefault();
HttpGet httpGet = new HttpGet("http://example.com");

CloseableHttpResponse response = httpClient.execute(httpGet);
try {
    if (response.getStatusLine().getStatusCode() == 200) {
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
    }
} finally {
    response.close();
    httpClient.close();
}
```

#### 5.x 版本
```java
try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
    HttpGet httpGet = new HttpGet("http://example.com");
    
    try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
        if (response.getCode() == 200) {
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }
}
```

### 超时配置对比

#### 4.5.x 版本
```java
RequestConfig config = RequestConfig.custom()
    .setConnectTimeout(5000)
    .setConnectionRequestTimeout(5000)
    .setSocketTimeout(5000)
    .build();
```

#### 5.x 版本
```java
RequestConfig config = RequestConfig.custom()
    .setConnectTimeout(Timeout.ofSeconds(5))
    .setConnectionRequestTimeout(Timeout.ofSeconds(5))
    .setResponseTimeout(Timeout.ofSeconds(5))
    .build();
```

## ✨ 5.x 版本新特性

1. **HTTP/2 支持** - 原生支持 HTTP/2 协议
2. **更好的性能** - 优化的连接池和请求处理
3. **Java Virtual Threads 兼容** - 支持 Java 21 的虚拟线程
4. **改进的 API** - 更现代化的 API 设计
5. **更好的资源管理** - 推荐使用 try-with-resources

## 🚀 迁移步骤

1. **更新 Maven 依赖**
   - 替换 groupId 和 artifactId
   - 更新到最新版本

2. **更新 Import 语句**
   - 批量替换包名
   - 使用 IDE 的重构功能

3. **更新 API 调用**
   - `getStatusLine().getStatusCode()` → `getCode()`
   - 超时配置使用 `Timeout` 类
   - 推荐使用 try-with-resources

4. **测试验证**
   - 运行现有测试
   - 验证功能正常

## ⚠️ 注意事项

1. **向后兼容性** - 5.x 不向后兼容 4.5.x
2. **第三方库** - 确保依赖的第三方库支持 5.x
3. **微信支付** - 您的项目中使用的微信支付库可能仍依赖 4.5.x
4. **渐进式迁移** - 可以考虑新代码使用 5.x，旧代码保持 4.5.x

## 📚 推荐资源

- [Apache HttpClient 5.x 官方文档](https://hc.apache.org/httpcomponents-client-5.5.x/)
- [迁移指南](https://hc.apache.org/httpcomponents-client-5.5.x/migration-guide/)
- [示例代码](https://hc.apache.org/httpcomponents-client-5.5.x/examples.html)
