package com.atguigu.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
public class SpringDataRedisTest {

//    @Autowired
    @SuppressWarnings("rawtypes")
    private RedisTemplate redisTemplate;

    @Test
    @SuppressWarnings("all")
    public void testRedis() {
        System.out.println(redisTemplate);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        HashOperations hashOperations = redisTemplate.opsForHash();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        ListOperations listOperations = redisTemplate.opsForList();
    }

    /**
     * 操作字符串类型的数据
     */
    @Test
    @SuppressWarnings("all")
    public void testString() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // set =  set(key, value)
        valueOperations.set("city", "北京");
        // get = get(key)
        String cityName = (String) valueOperations.get("city");
        System.out.println(cityName);
        // setex = set(key, value, timeout, timeUnit 时间单位)
        valueOperations.set("city", "上海", 10, TimeUnit.MINUTES);
        // setnx = setIfAbsent(key, value) 不存在则设置
        valueOperations.setIfAbsent("city", "广州");

        String city = (String) valueOperations.get("city");
        System.out.println(city);
    }

    /**
     * 操作hash类型的数据
     */
    @Test
    @SuppressWarnings("all")
    public void testHash() {
        HashOperations hashOperations = redisTemplate.opsForHash();
        // hset = put(key, field, value) 设置指定字段的值
        hashOperations.put("user:1", "name", "张三");
        hashOperations.put("user:1", "age", "18");
        hashOperations.put("user:1", "address", "北京");
        hashOperations.put("user:1", "phone", "13888888888");
        // hget = get(key, field) 获取指定字段的值
        String name = (String) hashOperations.get("user:1", "name");
        System.out.println(name);
        // hkeys = keys(key) 获取指定字段的所有值
        Set keys = hashOperations.keys("user:1");
        System.out.println(keys);
        // hvals = values(key) 获取指定字段的所有值
        List values = hashOperations.values("user:1");
        System.out.println(values);
        // hdel = delete(key, field) 删除指定字段
        hashOperations.delete("user:1", "phone");
    }

    /**
     * 操作list类型数据
     */
    @Test
    @SuppressWarnings("all")
    public void testList() {
        ListOperations listOperations = redisTemplate.opsForList();
        // lpush = leftPush(key, value) 从队头添加元素
        listOperations.leftPush("cityList", "北京");
        listOperations.leftPush("cityList", "上海");
        listOperations.leftPush("cityList", "广州");
        listOperations.leftPush("cityList", "深圳");
        // lrange = range(key, start, end) 获取指定范围的元素
        List cityList = listOperations.range("cityList", 0, -1);
        System.out.println(cityList);
        // rpop = rightPop(key) 移除并获取队尾元素
        String city = (String) listOperations.rightPop("cityList");
        System.out.println(city);
        // llen = size(key) 获取队列长度
        Long size = listOperations.size("cityList");
        System.out.println(size);
    }

    /**
     * 操作set类型数据
     */
    @Test
    @SuppressWarnings("all")
    public void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();
        // sadd = add(key, value) 添加元素
        setOperations.add("citySet", "北京", "上海", "广州", "深圳", "杭州");
        setOperations.add("citySetex", "杭州", "南京", "西安", "北京");
        // smembers = members(key) 获取所有元素
        Set citySet = setOperations.members("citySet");
        System.out.println(citySet);
        // srem = remove(key, value) 删除元素
        setOperations.remove("citySet", "广州");
        // scard = size(key) 获取元素个数
        Long size = setOperations.size("citySet");
        System.out.println(size);
        // sinter = intersect(key, otherKey) 获取两个集合的交集
        Set intersect = setOperations.intersect("citySet", "citySetex");
        System.out.println(intersect);
        // sunion = union(key, otherKey) 获取两个集合的并集
        Set union = setOperations.union("citySet", "citySetex");
        System.out.println(union);
    }

    /**
     * 操作zset类型数据
     */
    @Test
    @SuppressWarnings("all")
    public void testZSet() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        // zadd = add(key, value, score) 添加元素
        zSetOperations.add("cityZSet", "北京", 10);
        zSetOperations.add("cityZSet", "上海", 20);
        zSetOperations.add("cityZSet", "广州", 30);
        zSetOperations.add("cityZSet", "深圳", 40);
        // zrange = range(key, start, end) 获取指定范围的元素
        Set cityZSet = zSetOperations.range("cityZSet", 0, -1);
        System.out.println(cityZSet);
        // zrem = remove(key, value) 删除元素
        zSetOperations.remove("cityZSet", "广州");
        // zcard = size(key) 获取元素个数
        Long size = zSetOperations.size("cityZSet");
        System.out.println(size);
        // zscore = score(key, value) 获取元素的分数
        Double score = zSetOperations.score("cityZSet", "北京");
        System.out.println(score);
        // zincrby = incrementScore(key, value, increment) 为元素增加分数
        zSetOperations.incrementScore("cityZSet", "北京", 100);
    }

    /**
     * 通用命令操作
     */
    @Test
    @SuppressWarnings("all")
    public void testCommon() {
        // keys = keys(pattern) 获取所有key,支持集合
        Set keys = redisTemplate.keys("*");
        System.out.println( keys);
        // exists = exists(key) 判断key是否存在
        Boolean cityExists = redisTemplate.hasKey("city");
        System.out.println(cityExists);
        // type = type(key) 获取key的类型
        for (Object key : keys) {
            DataType type = redisTemplate.type(key);
            System.out.println(key + " : " + type);
        }

        // del = delete(key) 删除key
//        redisTemplate.delete("city");
    }
}
