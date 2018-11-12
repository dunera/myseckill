package com.dunera.seckill.service.impl;

import com.dunera.seckill.dao.UserMapper;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.service.UserService;
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
    public void addUser(String userName, String password, int avatar) {
        User user = new User();
        user.setUserName(userName);
        user.setMd5password(password);
        user.setAvater(avatar);
        userMapper.insert(user);
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        return false;
    }

    @Override
    public User getUserByUserName(String userName) {
        return null;
    }

    @Override
    public User getUserByUserId(Integer userId) {
        return null;
    }
}
