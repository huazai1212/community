package com.huazai.community.dao;

import com.huazai.community.model.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionDao {

    void addQuestion(Question question);
}
