package com.qf.ruigie.common;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 金桑
 * @Date: 2022/06/08/21:37
 * @Description:
 */

/**
 * 基于threadlocal封装工具类,用户保存和获取当前登入用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal =new ThreadLocal<>();

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
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
