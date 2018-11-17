package com.dunera.seckill.exception;

/**
 * @author lyx
 * @date 2018/11/17
 */
public class GlobalException extends RuntimeException {

    private CodeMessage codeMessage;

    public GlobalException(CodeMessage message) {
        super(message.toString());
        this.codeMessage = message;
    }

    public GlobalException(String message, Throwable cause, CodeMessage codeMessage) {
        super(message, cause);
        this.codeMessage = codeMessage;
    }

    public CodeMessage getCodeMessage() {
        return codeMessage;
    }
}
