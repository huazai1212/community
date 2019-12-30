package com.huazai.community.service;

import com.huazai.community.dao.UserMapper;
import com.huazai.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdateUser(User user) {
        User dbUser = userMapper.findUserByAccountId(user.getAccountId());

        if(dbUser == null){//需要新增用户

            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            userMapper.insert(user);

        }else{//需要更新用户

            //获取用户信息
            dbUser.setGmtModified(user.getGmtModified());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            //更新用户
            userMapper.updateUser(dbUser);
        }
    }

    //根据token查询用户
    public User findUserByToken(String token) {
        return userMapper.findUserByToken(token);
    }
}
