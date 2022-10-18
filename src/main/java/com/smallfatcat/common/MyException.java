package com.smallfatcat.common;

/**
 * @author zsz
 * @Description-自定义业务异常
 * @date 2022/10/6
 */
public class MyException extends RuntimeException{

    public MyException(String message) {
        super(message);
    }
}
