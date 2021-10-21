package com.boot.uploadFile.handler;

import com.boot.uploadFile.exception.BaseException;
import com.boot.uploadFile.exception.CustomException;
import com.boot.uploadFile.utils.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: WBin
 * @Date: 2021/3/15 16:08
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public JsonResult baseException(BaseException e) {
        return JsonResult.fail(e.getMessage(), null);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public JsonResult baseExceptionHandler(CustomException ex) {
        return JsonResult.ok(Boolean.FALSE, ex.getCode(), ex.getMessage(), null);
    }


}
