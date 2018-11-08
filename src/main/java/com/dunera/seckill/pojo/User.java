package com.dunera.seckill.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private Long userId;

    private String userName;

    private String phoneNumbedr;

    private Integer avater;

    private String md5password;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPhoneNumbedr() {
        return phoneNumbedr;
    }

    public void setPhoneNumbedr(String phoneNumbedr) {
        this.phoneNumbedr = phoneNumbedr == null ? null : phoneNumbedr.trim();
    }

    public Integer getAvater() {
        return avater;
    }

    public void setAvater(Integer avater) {
        this.avater = avater;
    }

    public String getMd5password() {
        return md5password;
    }

    public void setMd5password(String md5password) {
        this.md5password = md5password == null ? null : md5password.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getPhoneNumbedr() == null ? other.getPhoneNumbedr() == null : this.getPhoneNumbedr().equals(other.getPhoneNumbedr()))
            && (this.getAvater() == null ? other.getAvater() == null : this.getAvater().equals(other.getAvater()))
            && (this.getMd5password() == null ? other.getMd5password() == null : this.getMd5password().equals(other.getMd5password()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getPhoneNumbedr() == null) ? 0 : getPhoneNumbedr().hashCode());
        result = prime * result + ((getAvater() == null) ? 0 : getAvater().hashCode());
        result = prime * result + ((getMd5password() == null) ? 0 : getMd5password().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", phoneNumbedr=").append(phoneNumbedr);
        sb.append(", avater=").append(avater);
        sb.append(", md5password=").append(md5password);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}