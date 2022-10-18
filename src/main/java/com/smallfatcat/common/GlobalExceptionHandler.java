package com.smallfatcat.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author zsz
 * @Description-全局异常处理器
 * @date 2022/10/5
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})//拦截的controller
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return Result.error(msg);
        }
        return Result.error("未知错误!");
    }

    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result<String> exceptionHandler(MyException ex){
        log.error(ex.getMessage());
        return Result.error(ex.getMessage());
    }


}
