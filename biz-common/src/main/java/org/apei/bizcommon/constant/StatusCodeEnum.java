package org.apei.bizcommon.constant;

public enum StatusCodeEnum {
    OK(200, "Success"),
    ERROR(400, "Error");

    private final int code;
    private final String message;

    StatusCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
