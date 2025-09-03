package com.atguigu.constant;

/**
 * 状态常量，启用或者禁用
 */
public class StatusConstant {

    //启用
    public static final Integer ENABLE = 1;

    //禁用
    public static final Integer DISABLE = 0;

    //启用（字符串类型）
    public static final String ENABLE_STR = "1";

    //禁用（字符串类型）
    public static final String DISABLE_STR = "0";

    //是否为默认
    public static final Integer DEFAULT = 1;

    //是否非默认
    public static final Integer NOT_DEFAULT = 0;

    // WebSocket通知状态：来单提醒 1，催单提醒 2
    public static final Integer ORDER_REMINDER = 1;
    public static final Integer REMIND_ORDER = 2;
}

