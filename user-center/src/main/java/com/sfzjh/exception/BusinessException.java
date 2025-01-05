package com.sfzjh.exception;


import com.sfzjh.common.ErrorCode;
import lombok.Getter;

/**
 * 自定义业务异常
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29  17:43
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 异常码
     */
    private final int code;

    /**
     * 描述信息
     */
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

}
