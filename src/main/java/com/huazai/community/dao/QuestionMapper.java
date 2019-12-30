package com.huazai.community.dao;

import com.huazai.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QuestionMapper {

    //用户提问
    void addQuestion(Question question);

    //查询所有问题
    List<Question> getQuestionList(@Param("offset") Integer offset, @Param("size")Integer size);

    //获取用户的问题
    List<Question> getQuestionListByUser(@Param("userId")Integer userId, @Param("offset") Integer offset, @Param("size")Integer size);

    //统计有多少条问题
    Integer count();

    //统计用户有多少条提问
    Integer countByUserId(@Param("userId") Integer userId);
}
