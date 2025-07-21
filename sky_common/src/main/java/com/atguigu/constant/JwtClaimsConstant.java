package com.atguigu.constant;

/**
 * 这个类定义了JWT（JSON Web Token）中的声明（Claims）常量。
 * JWT是一种用于在各方之间安全传输信息的标准方法。在这个系统中，这些常量用于存储和传递用户相关的信息：
 */
public class JwtClaimsConstant {

    /**
     * EMP_ID：用于存储员工（employee）的ID标识
     * USER_ID：用于存储用户的ID标识
     * PHONE：用于存储用户的手机号码
     * USERNAME：用于存储用户的登录用户名
     * NAME：用于存储用户的真实姓名
     */
    public static final String EMP_ID = "empId";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String PHONE = "phone";
    public static final String NAME = "name";
}
