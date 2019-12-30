package com.huazai.community.service;

import com.huazai.community.dao.QuestionMapper;
import com.huazai.community.dao.UserMapper;
import com.huazai.community.dto.PaginationDTO;
import com.huazai.community.dto.QuestionDTO;
import com.huazai.community.model.Question;
import com.huazai.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;


    /**
     * 获取所有问题并带上用户信息
     * 1.查询所有的问题
     * 2.遍历问题集合查询用户信息
     * @return
     */
    public PaginationDTO getPaginationDTO(Integer currentpage, Integer size) {

        //查询总问题数
        Integer totalQuestionCount = questionMapper.count();
        //处理分页信息
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalQuestionCount, currentpage, size);

        //计算分页查询参数  limit offset，5
        Integer offset = (paginationDTO.getPage() - 1) * size;
        //查询所有的问题，不带用户（只带用户id，即Creator）
        List<Question> questionList =  questionMapper.getQuestionList(offset, size);
        //创建集合用于存放查出的questionDTOList，携带用户信息
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        //循环查询获得用户信息
        for(Question question : questionList){
            Integer userId = question.getCreator();//获取作者即用户
            User user = userMapper.findUserById(userId);

            //将查到的Question封装到QuestionDTO中
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            //将用户注入questionDTO中
            questionDTO.setUser(user);

            //将封装好的questionDTO放到questionDTOList集合中
            questionDTOList.add(questionDTO);
        }

        //封装questionDTOList到paginationDTO中
        paginationDTO.setQuestionDTOList(questionDTOList);





        return paginationDTO;
    }

    /**
     * 查询一个用户的问题
     * @param userId
     * @param currentpage
     * @param size
     * @return
     */
    public PaginationDTO getPaginationDTO(Integer userId, Integer currentpage, Integer size) {

        //查询总问题数
        Integer totalQuestionCount = questionMapper.countByUserId(userId);
        //处理分页信息
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalQuestionCount, currentpage, size);

        //计算分页查询参数  limit offset，5
        Integer offset = (paginationDTO.getPage() - 1) * size;
        //查询所有的问题，不带用户（只带用户id，即Creator）
        List<Question> questionList =  questionMapper.getQuestionListByUser(userId, offset, size);
        //创建集合用于存放查出的questionDTOList，携带用户信息
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        //循环查询获得用户信息
        for(Question question : questionList){
            User user = userMapper.findUserById(userId);

            //将查到的Question封装到QuestionDTO中
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            //将用户注入questionDTO中
            questionDTO.setUser(user);

            //将封装好的questionDTO放到questionDTOList集合中
            questionDTOList.add(questionDTO);
        }

        //封装questionDTOList到paginationDTO中
        paginationDTO.setQuestionDTOList(questionDTOList);

        return paginationDTO;
    }
}
