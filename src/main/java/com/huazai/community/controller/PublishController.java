package com.huazai.community.controller;

import com.huazai.community.dao.QuestionDao;
import com.huazai.community.dao.UserDao;
import com.huazai.community.model.Question;
import com.huazai.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private  QuestionDao questionDao;


    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String addQuestion(@RequestParam(value = "title", required = false)String title,
                              @RequestParam(value = "description", required = false)String description,
                              @RequestParam(value = "tag", required = false)String tag,
                              HttpServletRequest request,
                              Model model){
        /**
         * 1.判断用户是否登录再执行发布问题流程
         *  用户登陆后会在网页的session中存入一个token值，
         *  如果这个值与用户登录时存入数据库的值一致则就是已经在本次会话中登录了;
         * 2.为了能回显前端的数据，即用户如若未能发布问题成功还将原来用户填写的数据显示出来，这样就需要先将用户填写的信息
         *   放到model里面；
         * 3.校验数据是否为空，前后端都需要校验（如果只有前端校验有可能会被用户绕过，只能防君子不能防小人）
         */

        //先将用户填写的信息放到model里面；
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        //校验数据是否为空
        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        //验证用户是否登录（只要在本次会话中登录即可，登录后会将登陆标识放到cookies中）
        //先验证登陆
        User  user  = (User) request.getSession().getAttribute("user");

        if(user == null ){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        //设置问题属性
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        //新增问题
        questionDao.addQuestion(question);
        model.addAttribute("success", "问题发布成功");
        //返回界面
        return "redirect:/";//返回界面(重定向)
    }
}
