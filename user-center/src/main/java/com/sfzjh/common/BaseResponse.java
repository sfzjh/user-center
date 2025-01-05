package com.sfzjh.common;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 通用返回类
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29  17:42
 */
@Data
public class BaseResponse<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 数据
     */
    private T data;

    /**
     * 消息
     */
    private String message;

    /**
     * 描述
     */
    private String description;

    /**
     * 时间戳
     */
    private LocalDateTime timestamp;

    public BaseResponse(Integer code, T data, String message, String description) {
        this.timestamp = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(Integer code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(Integer code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
