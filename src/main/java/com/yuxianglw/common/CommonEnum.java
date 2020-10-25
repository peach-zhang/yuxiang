package com.yuxianglw.common;

import lombok.Getter;

@Getter
public enum CommonEnum {

    ACCOUNT_NUMBER_ACTIVE("Active","账号正常!"),

    ACCOUNT_NUMBER_LOCK("123","账号已锁定!");

    /*code*/
    private String code;

    /*信息*/
    private String msg;

    CommonEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
