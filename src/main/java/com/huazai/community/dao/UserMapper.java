package com.huazai.community.dao;

import com.huazai.community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    //增加用户
    void insert(User user);

    //根据token查询用户id
    User findUserByToken(String token);

    //根据id查询用户
    @Select("select * from user where id = #{id}")
    User findUserById(@Param("id")Integer id) ;

    //根据AccountId查询用户
    @Select("select * from user where accountId = #{accountId}")
    User findUserByAccountId(@Param("accountId")String accountId);

    //更新用户
    @Update("update user set name = #{name}, token = #{token}, gmtModified = #{gmtModified}, avatarUrl = #{avatarUrl} where accountId = #{accountId}" )
    void updateUser(User dbUser);

}
