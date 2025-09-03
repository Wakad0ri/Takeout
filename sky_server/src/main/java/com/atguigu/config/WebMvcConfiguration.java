package com.atguigu.config;

import com.atguigu.interceptor.JwtTokenAdminInterceptor;
import com.atguigu.json.JacksonObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import java.util.List;

/**
 * 配置类
 * 作用：注册web层相关组件（拦截器，控制器，视图解析器，消息转换器）
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册自定义拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public OpenAPI openAPI() {
        log.info("开始生成接口文档...");
        return new OpenAPI()
                .info(new Info()
                        .title("苍穹外卖项目接口文档")
                        .version("2.0")
                        .description("苍穹外卖项目接口文档"));
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射...");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 添加跨域配置
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        log.info("开始配置跨域...");
        registry.addMapping("/**") // 所有接口
                .allowedOriginPatterns("*") // 所有源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方式
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true) // 是否允许cookie
                .maxAge(3600); // 跨域允许时间
    }

    /**
     * 扩展 Spring MVC框架 的消息转换器
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        
        // 设置对象转换器，底层使用Jackson将Java对象转为JSON
        converter.setObjectMapper(new JacksonObjectMapper());
        
        // 将自定义的消息转换器加入容器中，并设置优先级
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                // 如果是 Knife4j 的转换器，跳过
                if (converters.get(i).getSupportedMediaTypes().toString().contains("application/json-patch+json")) {
                    continue;
                }
                converters.set(i, converter);
                return;
            }
        }
        // 如果没找到可替换的转换器，就添加到第一个位置
        converters.add(0, converter);
    }
}

