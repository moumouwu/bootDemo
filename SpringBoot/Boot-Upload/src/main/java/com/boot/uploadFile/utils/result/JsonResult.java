package com.boot.uploadFile.utils.result;

import com.boot.uploadFile.constant.ResultCode;

import java.io.Serializable;

/**
 * @Author: binSin
 * @Description: 统一返回实体
 * @Date: 2021/3/10 11:25
 */
public class JsonResult<T> implements Serializable {
    private Boolean success;
    private Integer errorCode;
    private String errorMsg;
    private T data;

    public JsonResult() {
    }

    public JsonResult(Boolean success, ResultCode resultEnum, T data) {
        this.success = success;
        this.errorCode = resultEnum.getCode();
        this.errorMsg = resultEnum.getMessage();
        this.data = data;
    }

    public JsonResult(Boolean success, Integer errorCode, String errorMsg, T data) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public JsonResult(boolean success) {
        this.success = success;
        this.errorCode = success ? ResultCode.SUCCESS.getCode() : ResultCode.COMMON_FAIL.getCode();
        this.errorMsg = success ? ResultCode.SUCCESS.getMessage() : ResultCode.COMMON_FAIL.getMessage();
    }

    public JsonResult(boolean success, ResultCode resultEnum) {
        this.success = success;
        this.errorCode = success ? ResultCode.SUCCESS.getCode() : (resultEnum == null ? ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
        this.errorMsg = success ? ResultCode.SUCCESS.getMessage() : (resultEnum == null ? ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
    }

    public JsonResult(boolean success, T data) {
        this.success = success;
        this.errorCode = success ? ResultCode.SUCCESS.getCode() : ResultCode.COMMON_FAIL.getCode();
        this.errorMsg = success ? ResultCode.SUCCESS.getMessage() : ResultCode.COMMON_FAIL.getMessage();
        this.data = data;
    }

    public JsonResult(boolean success, ResultCode resultEnum, T data) {
        this.success = success;
        this.errorCode = success ? ResultCode.SUCCESS.getCode() : (resultEnum == null ? ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
        this.errorMsg = success ? ResultCode.SUCCESS.getMessage() : (resultEnum == null ? ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static JsonResult success() {
        return new JsonResult(Boolean.TRUE, 200, "成功", null);
    }

    public static <T> JsonResult success(T data) {
        return new JsonResult(Boolean.TRUE, 200, "成功", data);
    }

    public static <T> JsonResult success(ResultCode resultCode, T data) {
        return new JsonResult(Boolean.TRUE, resultCode, data);
    }

    public static JsonResult fail() {
        return new JsonResult(Boolean.TRUE, ResultCode.COMMON_FAIL);
    }

    public static <T> JsonResult fail(String msg, T data) {
        return new JsonResult(Boolean.TRUE, 40000, msg, data);
    }

    public static JsonResult fail(ResultCode resultEnum) {
        return new JsonResult(false, resultEnum);
    }

    public static <T> JsonResult ok(Boolean success, Integer errorCode, String errorMsg, T data) {
        return new JsonResult(success, errorCode, errorMsg, data);
    }
}
