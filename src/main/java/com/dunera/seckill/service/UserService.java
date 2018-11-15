package com.dunera.seckill.service;

import com.dunera.seckill.pojo.User;

/**
 * @author lyx
 * @date 2018/11/9
 */
public interface UserService {

    void addUser(String userName, String password, int avatar);

    boolean checkPassword(String userName, String password);

    User getUserByUserName(String userName);

    User getUserByUserId(Long userId);

    boolean checkUserNameIllegal(String userName);

}
