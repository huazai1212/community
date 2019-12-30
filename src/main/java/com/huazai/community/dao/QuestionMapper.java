package com.huazai.community.dao;

import com.huazai.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    //用户提问
    @Select("insert into question (id, title, description, gmtCreate, gmtModified, creator, commentCount, viewCount, likeCount, tag) " +
            "values" +
                                  "(null,#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void addQuestion(Question question);

    //查询所有问题
    @Select("select * from question limit #{offset} , #{size}")
    List<Question> getQuestionList(@Param("offset") Integer offset, @Param("size")Integer size);

    //获取用户的问题
    @Select("select * from question where creator=#{userId} limit #{offset} , #{size}")
    List<Question> getQuestionListByUser(@Param("userId")Integer userId, @Param("offset") Integer offset, @Param("size")Integer size);

    //统计有多少条问题
    @Select("select count(1) from question")
    Integer count();

    //统计用户有多少条提问
    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);
}
