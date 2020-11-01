package com.yuxianglw.common;

import lombok.Getter;

/**
 *
 * 业务错误码：返回结果的状态码
 *
 * 如果想要代码更具维护性一点,可以定义不同种类的错误码,都实现 BaseCodeInterface
 * @Author zhangtao
 * @Date 2020/3/1 14:51
 * @Version 1.0
 **/
@Getter
public enum  ErrorCodeEnum implements BaseCodeInterface {

    // 数据操作错误定义
    BODY_NOT_MATCH(400," 请求的数据格式不符! "),
    SIGNATURE_NOT_MATCH(401," 请求的数字签名不匹配! "),
    NOT_FOUND(404, " 未找到该资源! "),
    INTERNAL_SERVER_ERROR(500, " 服务器内部错误! "),
    SERVER_BUSY(503," 服务器正忙，请稍后再试! "),
    //用户相关
    USER_PWD_ACCOUNT_NOT_FOUND(50001, " 账号/密码错误! "),
    LOGIN_NO_TOKEN(50002, " Token无效，您无权访问该接口! "),
    DISALLOW_CURRENT_USER(10003," 不允许禁用当前用户 "),
    LOGIN_OUT_FAIL(50004," 退出失败 "),
    INVALID_TOKEN(50005," token错误，请重新登入！"),
    EXPIRED_TOKEN(50006," token过期，请重新登入！");
    /** 错误码 */
    private int resultCode;

    /** 错误描述 */
    private String resultMsg;

    ErrorCodeEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

}
