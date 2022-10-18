package com.smallfatcat.common;

/**
 * @author zsz
 * @Description-基于threadlocal的一个工具类，用于获取当前登录的用户id
 * 数据隔离，数据副本，每个线程不一样
 * @date 2022/10/6
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

}
