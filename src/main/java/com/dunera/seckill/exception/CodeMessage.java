package com.dunera.seckill.exception;

/**
 * @author lyx
 * @date 2018/11/17
 */
public class CodeMessage {

    private int code;
    private String message;

    public CodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    //通用
    public static final CodeMessage SUCCESS = new CodeMessage(0, "成功");
    public static final CodeMessage FAIL = new CodeMessage(1, "失败");
    public static final CodeMessage SERVER_ERROR = new CodeMessage(500001, "系统处理异常");

    //登录注册模块
    public static final CodeMessage USER_REPEAT = new CodeMessage(600101, "用户名已存在");

    public static final CodeMessage USER_NOT_LOGIN = new CodeMessage(600201, "用户未登录");
    public static final CodeMessage USER_PASSWD_WRONG = new CodeMessage(600202, "用户名或密码错误");

    //商品展示
    public static final CodeMessage GOOD_UNACTIVE = new CodeMessage(700101, "商品无效");

    //秒杀处理
    public static final CodeMessage SEK_STOCK_NOT_ENOUGH = new CodeMessage(800101, "库存不足");
    public static final CodeMessage SEK_REPEAT_ORDER = new CodeMessage(800102, "重复下单");
    public static final CodeMessage SEK_END = new CodeMessage(800103, "秒杀已结束");

    @Override
    public String toString() {
        return "CodeMessage{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
