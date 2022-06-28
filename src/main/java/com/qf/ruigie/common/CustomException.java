package com.qf.ruigie.common;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 金桑
 * @Date: 2022/06/27/15:07
 * @Description:
 *
 * 自定义业务异常
 */

public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
