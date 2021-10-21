package com.boot.uploadFile.exception;

/**
 * @Author: binSin
 * @Date 2021/3/10 11:20
 * <p>自定义错误类</p>
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, int code) {
        super(message);
        this.code = code;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
