package com.mmall.common;

/**
 * 响应枚举类
 * @author qinfen
 */
public enum ResponseCode {

    /**
     * 0:成功
     */
    SUCCESS(0,"SUCCESS"),
    /**
     * 1：失败
     */
    ERROR(1,"ERROR"),
    /**
     * 10：需要登录
     */
    NEED_LOGIN(10,"NEED_LOGIN"),
    /**
     * 2：错误的参数
     */
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
