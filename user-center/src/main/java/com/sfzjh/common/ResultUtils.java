package com.sfzjh.common;


/**
 * 返回工具类
 *
 * @author Sunny
 * @version 1.0.0
 * @since 2024-12-29  17:42
 */
public class ResultUtils {

    /**
     * 成功返回数据
     *
     * @param data 要返回的数据
     * @return com.sfzjh.common.BaseResponse<T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 返回失败
     *
     * @param errorCode 错误码
     * @return com.sfzjh.common.BaseResponse
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败返回
     *
     * @param code 错误码
     * @param message 消息
     * @param description 描述
     * @return com.sfzjh.common.BaseResponse
     */
    public static <T> BaseResponse<T> error(Integer code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }

    /**
     * 返回失败
     *
     * @param errorCode 错误码
     * @param message 消息
     * @param description 描述
     * @return com.sfzjh.common.BaseResponse
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, message, description);
    }

    /**
     *
     *
     * @param errorCode 错误码
     * @param description 描述
     * @return com.sfzjh.common.BaseResponse<T>
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode, String description) {
        return error(errorCode.getCode(), errorCode.getMessage(), description);
    }

}
