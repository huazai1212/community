package com.huazai.community.dao;

import com.huazai.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionDao {

    @Select("insert into question (id, title, description, gmtCreate, gmtModified, creator, commentCount, viewCount, likeCount, tag) " +
            "values" +
                                  "(null,#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void addQuestion(Question question);

    @Select("select * from question limit #{offset} , #{size}")
    List<Question> getQuestionList(@Param("offset") Integer offset, @Param("size")Integer size);

    @Select("select count(1) from question")
    Integer count();//统计有多少条问题
}
