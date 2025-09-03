package com.atguigu.config;

import com.atguigu.json.JacksonObjectMapper;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Spring Cache 配置类
 * 
 * 功能：
 * 1. 配置 Redis 作为 Spring Cache 的底层实现
 * 2. 设置缓存的序列化方式
 * 3. 设置缓存的过期时间
 * 4. 支持多种缓存配置
 */
@Configuration
@Slf4j
public class CacheConfiguration {

    /**
     * 配置 Spring Cache 使用 Redis 作为缓存管理器
     * 
     * @param redisConnectionFactory Redis连接工厂
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始配置Spring Cache使用Redis...");
        
        // 使用自定义的JacksonObjectMapper来支持LocalDateTime序列化
        JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
        
        // 禁用默认的类型信息，避免反序列化时的类型转换问题
        jacksonObjectMapper.deactivateDefaultTyping();
        
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(jacksonObjectMapper);
        
        // 配置序列化方式
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 设置key的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value的序列化方式（使用自定义的Jackson配置）
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer))
                // 设置缓存过期时间为30分钟
                .entryTtl(Duration.ofMinutes(30))
                // 不缓存空值
                .disableCachingNullValues();

        // 构建缓存管理器
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .transactionAware() // 支持事务
                .build();
    }
}
