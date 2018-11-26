package com.dunera.seckill.exception;

/**
 * @author lyx
 * @date 2018/11/17
 */
public class ErrorMessage {

    private String code;
    private String message;

    public ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 系统异常错误
     */
    public static final ErrorMessage SYS_ERROR = new ErrorMessage("S9999", "系统处理错误");
    public static final ErrorMessage SYS_PARAM_NULL = new ErrorMessage("S1001", "参数不能为空");
    public static final ErrorMessage SYS_PARAM_ILLEGAL = new ErrorMessage("S1002", "参数校验不通过");

    /**
     * 登录注册模块
     */
    public static final ErrorMessage USER_REPEAT = new ErrorMessage("U1001", "用户已存在");
    public static final ErrorMessage USER_NOT_LOGIN = new ErrorMessage("U1002", "用户未登录");
    public static final ErrorMessage USER_PASSWD_WRONG = new ErrorMessage("U1003", "用户名或密码错误");
    public static final ErrorMessage USER_EMPTY = new ErrorMessage("U1003", "用户名或密码不能为空");

    /**
     * 商品展示
     */
    public static final ErrorMessage GOOD_UNACTIVE = new ErrorMessage("G1001", "商品无效");

    /**
     * 秒杀处理
     */
    public static final ErrorMessage SEK_STOCK_NOT_ENOUGH = new ErrorMessage("K1001", "库存不足");
    public static final ErrorMessage SEK_REPEAT_ORDER = new ErrorMessage("K1002", "重复下单");
    public static final ErrorMessage SEK_ENDED = new ErrorMessage("K1003", "秒杀已结束");

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
