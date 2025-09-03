package com.atguigu.service;

import com.atguigu.DTO.UserType.UserLoginDTO;
import com.atguigu.Entity.User;

public interface UserService {

    User wxlogin(UserLoginDTO userLoginDTO);

}
