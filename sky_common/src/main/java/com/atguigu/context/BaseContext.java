package com.atguigu.context;

/**
 * 这是一个基于ThreadLocal的工具类
 * 主要用于在同一个线程中存储和传递用户标识信息。具体作用如下：
 */
public class BaseContext {


    // 使用ThreadLocal来存储数据，确保在多线程环境下数据的隔离性
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * setCurrentId(Long id)：设置当前线程的用户ID
     * 作用：在用户登录后，将用户ID存储到ThreadLocal中
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * getCurrentId()：获取当前线程的用户ID
     * 作用：在需要获取用户ID的地方调用该方法，获取当前线程的用户ID
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /**
     * removeCurrentId()：移除当前线程的用户ID
     * 作用：在用户退出登录后，将用户ID从ThreadLocal中移除
     */
    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
