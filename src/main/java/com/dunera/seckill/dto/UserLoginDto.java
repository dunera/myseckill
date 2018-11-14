package com.dunera.seckill.dto;

/**
 * @author lyx
 * @date 2018/11/14
 */
public class UserLoginDto {

    public UserLoginDto() {
    }

    public UserLoginDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    private String userName;

    private String password;

    private String confirmPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
