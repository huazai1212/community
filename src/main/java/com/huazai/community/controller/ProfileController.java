package com.huazai.community.controller;

import com.huazai.community.dto.PaginationDTO;
import com.huazai.community.model.User;
import com.huazai.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;

    /**
     *
     * @param action
     * @param model
     * @return
     */


    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1")Integer currentpage, //页码
                          @RequestParam(name = "size",defaultValue = "3")Integer size){//页面大小

        //先验证登陆
        User  user  = (User) request.getSession().getAttribute("user");

        if(user == null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        }
        if("replies".equals(action)){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        //获取带用户信息的paginationDTO
        PaginationDTO paginationDTO =  questionService.getPaginationDTO(user.getId(),currentpage, size);
        model.addAttribute("paginationDTO",paginationDTO);
        //返回页面
        return "profile";
    }
}
