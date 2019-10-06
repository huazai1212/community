package com.huazai.community.controller;

import com.huazai.community.dto.PaginationDTO;
import com.huazai.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */

@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;
    private PaginationDTO paginationDTO;


    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @RequestParam(name = "page",defaultValue = "1")Integer Currentpage,//页码
                        @RequestParam(name = "size",defaultValue = "3")Integer size,//页面大小，即每页几条
                        Model model){


        //获取带用户信息的paginationDTO
        PaginationDTO paginationDTO =  questionService.getPaginationDTO(Currentpage, size);
        model.addAttribute("paginationDTO",paginationDTO);

        return "index";
    }
}
