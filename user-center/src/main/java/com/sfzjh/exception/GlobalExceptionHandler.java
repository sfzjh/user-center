package com.sfzjh.exception;


import com.sfzjh.common.BaseResponse;
import com.sfzjh.common.ErrorCode;
import com.sfzjh.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29  17:43
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException exception) {
        log.error("businessException: {}", exception.getMessage());
        return ResultUtils.error(exception.getCode(), exception.getMessage(), exception.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException exception) {
        log.error("runtimeException", exception);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, exception.getMessage(), "");
    }

}
