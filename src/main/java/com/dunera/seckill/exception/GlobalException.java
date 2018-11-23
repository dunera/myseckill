package com.dunera.seckill.exception;

/**
 * @author lyx
 * @date 2018/11/17
 */
public class GlobalException extends RuntimeException {

    private ErrorMessage errorMessage;

    public GlobalException(ErrorMessage message) {
        super(message.toString());
        this.errorMessage = message;
    }

    public GlobalException(String message, Throwable cause, ErrorMessage errorMessage) {
        super(message, cause);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
