package com.yuxianglw.common;

public class CommonConstant {

    /**
     * 认证
     */
    public static final String AUTHENTICATIONCACHE = "AuthenticationCache";
    /**
     * 授权
     */
    public static final String AUTHORIZATIONCACHE = "AuthorizationCache";

	/**
	 * 删除标志
	 */
	public static final String DEL_FLAG_1 = "1";

	/**
	 * 未删除
	 */
	public static final String DEL_FLAG_0 = "0";

	/**
	 * 系统日志类型： 登录
	 */
	public static final int LOG_TYPE_1 = 1;

	/**
	 * 系统日志类型： 操作
	 */
	public static final int LOG_TYPE_2 = 2;

	/**
	 * 操作日志类型： 查询
	 */
	public static final int OPERATE_TYPE_1 = 1;

	/**
	 * 操作日志类型： 添加
	 */
	public static final int OPERATE_TYPE_2 = 2;

	/**
	 * 操作日志类型： 更新
	 */
	public static final int OPERATE_TYPE_3 = 3;

	/**
	 * 操作日志类型： 删除
	 */
	public static final int OPERATE_TYPE_4 = 4;

	/**
	 * 操作日志类型： 倒入
	 */
	public static final int OPERATE_TYPE_5 = 5;

	/**
	 * 操作日志类型： 导出
	 */
	public static final int OPERATE_TYPE_6 = 6;

    /**
     * 500
     */
    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 5000;

    /**
     * 200
     */
    public static final Integer OK_200 = 200;

    /**访问权限认证未通过 510*/
    public static final Integer SC_JEECG_NO_AUTHZ=510;

}
