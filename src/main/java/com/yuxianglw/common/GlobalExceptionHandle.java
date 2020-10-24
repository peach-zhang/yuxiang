package com.yuxianglw.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle<T> {

    @ResponseStatus(HttpStatus.OK)
    public Result sendSuccessResponse(){
        return Result.ok();
    }

    @ResponseStatus(HttpStatus.OK)
    public Result sendSuccessResponse(T data) {
        return Result.ok(data);
    }

    @ExceptionHandler(ServiceException.class)
    public Result sendErrorResponseService(ServiceException exception){
        return Result.error(exception.getErrorCode(),exception.getErrorMsg());
    }

    @ExceptionHandler(Exception.class)
    public Result sendErrorResponseSystem(Exception exception){
        return Result.error("系统错误");
    }
}
