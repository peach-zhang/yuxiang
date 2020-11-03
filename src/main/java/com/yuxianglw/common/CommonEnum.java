package com.yuxianglw.common;

import lombok.Getter;

@Getter
public enum CommonEnum {

    ACCOUNT_NUMBER_ACTIVE("Active","账号正常!","正常"),

    ACCOUNT_NUMBER_LOCK("Lock","账号已锁定!" ,"锁定");

    /*code*/
    private String code;

    /*信息*/
    private String msg;

    /*状态*/
    private String status;

    CommonEnum(String code, String msg,String status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
    }
}
