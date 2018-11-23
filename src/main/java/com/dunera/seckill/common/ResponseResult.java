package com.dunera.seckill.common;

import com.dunera.seckill.exception.ErrorMessage;

/**
 * @author lyx
 * @date 2018/11/23
 */
public class ResponseResult<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;

    public ResponseResult(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ResponseResult(ErrorMessage errorMessage) {
        if (errorMessage != null) {
            this.success = false;
            this.code = errorMessage.getCode();
            this.message = errorMessage.getMessage();
        }
    }

    public ResponseResult(ErrorMessage errorMessage, T data) {
        if (errorMessage != null) {
            this.success = false;
            this.code = errorMessage.getCode();
            this.message = errorMessage.getMessage();
            this.data = data;
        }
    }

    public ResponseResult(T data) {
        this.data = data;
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(true, data);
    }

    public static <T> ResponseResult<T> error(T data) {
        return new ResponseResult<>(false, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
