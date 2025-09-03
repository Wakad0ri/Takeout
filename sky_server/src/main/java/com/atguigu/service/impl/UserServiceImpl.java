package com.atguigu.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.atguigu.DTO.UserType.UserLoginDTO;
import com.atguigu.Entity.User;
import com.atguigu.constant.MessageConstant;
import com.atguigu.exception.LoginFailedException;
import com.atguigu.mapper.UserMapper;
import com.atguigu.properties.WeChatProperties;
import com.atguigu.service.UserService;
import com.atguigu.utils.HttpClient5Util;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Tag(name = "用户管理-服务层")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatProperties weChatProperties;

    // 微信服务接口地址：
    private static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 微信登录
     * @param userLoginDTO （json）
     * @return User
     */
    @Override
    public User wxlogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO);

        // 判断openid是否为空，如果是则抛出业务异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        // 判断当前用户是否为新用户，如果是新用户则自动完成注册
        User user = userMapper.getByOpenid(openid);
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            // 问：为什么只需要填补这几个数据呢？
            // 答：因为这个用户是微信用户，User 实体的数据来源的其他字段（name, phone, sex 等）：在注册时都是空的，用户后续可以完善
            userMapper.insert(user);
        }

        return user;

    }

    private static String getOpenid(UserLoginDTO userLoginDTO) {
        // 调用微信接口服务，获得当前微信用户的OpenID（若为空则抛出业务异常）
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", "wxe6d729502a0a743b");
        paramMap.put("secret", "b7acb15b72ba38d6a2c588c915c5b595");
        paramMap.put("js_code", userLoginDTO.getCode());
        paramMap.put("grant_type", "authorization_code");
        String json = HttpClient5Util.doGet(WX_LOGIN_URL, paramMap);

        JSONObject response = JSONObject.parseObject(json);
        return response.getString("openid");
        // 等效于：String openid = response.getString("openid"); 然后return
    }
}
package com.atguigu.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.atguigu.DTO.UserType.UserLoginDTO;
import com.atguigu.Entity.User;
import com.atguigu.constant.MessageConstant;
import com.atguigu.exception.LoginFailedException;
import com.atguigu.mapper.UserMapper;
import com.atguigu.properties.WeChatProperties;
import com.atguigu.service.UserService;
import com.atguigu.utils.HttpClient5Util;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Tag(name = "用户管理-服务层")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatProperties weChatProperties;

    // 微信服务接口地址：
    private static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 微信登录
     * @param userLoginDTO （json）
     * @return User
     */
    @Override
    public User wxlogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO);

        // 判断openid是否为空，如果是则抛出业务异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        // 判断当前用户是否为新用户，如果是新用户则自动完成注册
        User user = userMapper.getByOpenid(openid);
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            // 问：为什么只需要填补这几个数据呢？
            // 答：因为这个用户是微信用户，User 实体的数据来源的其他字段（name, phone, sex 等）：在注册时都是空的，用户后续可以完善
            userMapper.insert(user);
        }

        return user;

    }

    private static String getOpenid(UserLoginDTO userLoginDTO) {
        // 调用微信接口服务，获得当前微信用户的OpenID（若为空则抛出业务异常）
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", "wxe6d729502a0a743b");
        paramMap.put("secret", "b7acb15b72ba38d6a2c588c915c5b595");
        paramMap.put("js_code", userLoginDTO.getCode());
        paramMap.put("grant_type", "authorization_code");
        String json = HttpClient5Util.doGet(WX_LOGIN_URL, paramMap);

        JSONObject response = JSONObject.parseObject(json);
        return response.getString("openid");
        // 等效于：String openid = response.getString("openid"); 然后return
    }
}
