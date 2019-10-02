package com.huazai.community.dao;

import com.huazai.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {


    //增加用户
    void insert(User user);

    //
    User findUserByToken(String token);
}
