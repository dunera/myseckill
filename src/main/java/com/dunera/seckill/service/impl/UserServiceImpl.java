package com.dunera.seckill.service.impl;

import com.dunera.seckill.dao.UserMapper;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.service.UserService;
import com.dunera.seckill.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lyx
 * @date 2018/11/10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(String userName, String password, String phoneNum, int avatar) {
        User user = new User();
        user.setUserName(userName);
        user.setMd5password(password);
        user.setphoneNumber(phoneNum);
        user.setAvater(avatar);
        userMapper.insert(user);
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        User user = getUserByUserName(userName);
        return user != null && MD5.checkMD5(password, user.getMd5password());
    }

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public User getUserByUserId(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean checkUserNameIllegal(String userName) {
        return getUserByUserName(userName) == null;
    }
}
