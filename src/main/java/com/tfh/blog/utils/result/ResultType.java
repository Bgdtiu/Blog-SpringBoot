package com.tfh.blog.utils.result;

import lombok.Getter;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/18 18:57
 * TODO: 结果枚举
 */
@Getter
public enum ResultType {
    SUCCESS(2000, "成功"),
    ERROR(2001, "失败"),
    PERMISSION_DENIED(2002, "需要完全身份验证才能访问此接口"),
    USER_ALREADY_EXISTS(2003, "用户已存在"),
    PERMISSION_DELETION_FAILED(2004,"至少需要有一个权限"),
    LOGOUT_ERROR(2005,"退出失败")
    ;


    ResultType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final int code;
    private final String msg;
}
