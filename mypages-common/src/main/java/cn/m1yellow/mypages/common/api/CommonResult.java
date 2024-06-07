package cn.m1yellow.mypages.common.api;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 通用返回对象
 * Created by macro on 2019/4/19.
 */
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private long code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 数据封装
     */
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功返回结果
     *
     * @param message
     */
    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }


    /**
     * 失败返回结果，默认
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }


    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
        return new CommonResult<T>(errorCode.getCode(), message, null);
    }


    /**
     * 参数验证失败返回结果，默认
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        if (StringUtils.isBlank(message)) {
            message = ResultCode.VALIDATE_FAILED.getMessage();
        }
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }


    /**
     * 未登录返回结果，默认
     */
    public static <T> CommonResult<T> unauthorized() {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), null);
    }

    /**
     * 未登录返回结果，可传信息
     */
    public static <T> CommonResult<T> unauthorized(String message) {
        if (StringUtils.isBlank(message)) {
            message = ResultCode.UNAUTHORIZED.getMessage();
        }
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), null);
    }


    /**
     * 未授权返回结果，默认
     */
    public static <T> CommonResult<T> forbidden() {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), null);
    }

    /**
     * 未授权返回结果，可传信息
     */
    public static <T> CommonResult<T> forbidden(String message) {
        if (StringUtils.isBlank(message)) {
            message = ResultCode.FORBIDDEN.getMessage();
        }
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), message, null);
    }


    /**
     * 资源未找到返回结果，默认
     */
    public static <T> CommonResult<T> notFoundFailed() {
        return failed(ResultCode.NOT_FOUND);
    }

    /**
     * 资源未找到返回结果，可传信息
     */
    public static <T> CommonResult<T> notFoundFailed(String message) {
        return new CommonResult<T>(ResultCode.NOT_FOUND.getCode(), message, null);
    }

    /**
     * 资源未找到返回结果，可传结果
     */
    public static <T> CommonResult<T> notFoundFailed(T data) {
        return new CommonResult<T>(ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getMessage(), data);
    }

    /**
     * 资源未找到返回结果，可传信息和结果
     */
    public static <T> CommonResult<T> notFoundFailed(String message, T data) {
        return new CommonResult<T>(ResultCode.NOT_FOUND.getCode(), message, data);
    }


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
