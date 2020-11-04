package com.yuxianglw.common;


/**
 * 业务静态常量
 * @author zhangtao
 */
public class BizConstant {
    //参数为空
    public static final String PARAMETER_IS_EMPTY = "参数为空！";
    //无对应用户
    public static final String NO_CORRESPONDING_USER = "无对应用户！";
    //超级管理员
    public static final String SUPER_ADMINISTRATOR = "zhangsan";
    //不可禁止
    public static final String NOT_FORBIDDEN = "超级管理员不可锁定！";
    //不可禁止自己
    public static final String DONT_BAN_YOURSELF = "不可锁定自己！";
    //操作成功
    public static final String SUCCESSFUL_OPERATION = "操作成功！";
    //认证缓存key
    public static final String AUTHENTICATION_CACHE = "shiro:cache:AuthenticationCache:";
    //授权缓存key
    public static final String AUTHORIZATION_CACHE = "shiro:cache:AuthorizationCache:";
    //缓存toke key
    public static final String CACHE_TOKEN = ":token";
    //缓存user key
    public static final String CACHE_USER = ":user";

}
