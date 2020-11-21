package com.yuxianglw.common;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.AuthorizationException;
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

    @ExceptionHandler(AuthorizationException.class)
    public Result handleAuthorizationException(){
        return Result.error(ErrorCodeEnum.NO_RIGHT_TO_OPERATE.getResultCode(),ErrorCodeEnum.NO_RIGHT_TO_OPERATE.getResultMsg());
    }
    @ExceptionHandler(AuthenticationException.class)
    public Result handleAuthenticationException(){
        return Result.error(ErrorCodeEnum.USER_PWD_ACCOUNT_NOT_FOUND.getResultCode(),ErrorCodeEnum.USER_PWD_ACCOUNT_NOT_FOUND.getResultMsg());
    }

    @ExceptionHandler(Exception.class)
    public Result sendErrorResponseSystem(Exception exception){
        return Result.error(exception.getMessage());
    }
}
