package com.example.project.exception;

public enum ErrorCode {
    USER_NOTFOUND(1001, "User Not Found"),
    USER_NOT_ENROLLED(1002,"User Not Enrolled"),
    USER_ALREADY_EXISTS(1003,"User Already Exists"),
    PASSWORD_INVALID(1004,"Password Invalid"),
    ROLE_NOTFOUND(2001, "ROLE NOT FOUND"),
    COURSE_NOTFOUND(3001,"COURSE_NOTFOUND"),
    TITLE_EXISTS(4001,"Title already exists"),
    ORDER_NOTFOUND(4001,"ORDER_NOTFOUND");
    private final int code;
    private final String message;
    ErrorCode(int code, String message) {
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
