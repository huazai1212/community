package com.huazai.community.dao;

import com.huazai.community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {

    //增加用户
    void insert(User user);

    //根据token查询用户id
    User findUserByToken(String token);

    //根据id查询用户
    User findUserById(@Param("id")Integer id) ;

    //根据AccountId查询用户
    User findUserByAccountId(@Param("accountId")String accountId);

    //更新用户
    void updateUser(User dbUser);

}
