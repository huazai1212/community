package com.huazai.community.dao;

import com.huazai.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    //增加用户
    void insert(User user);

    //根据token查询用户id
    User findUserByToken(String token);

    //根据id查询用户
    @Select("select * from user where id = #{id}")
    User findUserById(@Param("id")Integer id) ;
}
