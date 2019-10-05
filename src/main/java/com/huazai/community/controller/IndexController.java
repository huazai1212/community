package com.huazai.community.controller;

import com.huazai.community.dao.QuestionDao;
import com.huazai.community.dao.UserDao;
import com.huazai.community.dto.PaginationDTO;
import com.huazai.community.dto.QuestionDTO;
import com.huazai.community.model.Question;
import com.huazai.community.model.User;
import com.huazai.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */

@Controller
public class IndexController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionService questionService;
    private PaginationDTO paginationDTO;


    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @RequestParam(name = "page",defaultValue = "1")Integer Currentpage,//页码
                        @RequestParam(name = "size",defaultValue = "3")Integer size,//页面大小，即每页几条
                        Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0){
            return "index";
        }
        for(Cookie cookie : cookies){
            if("token".equals(cookie.getName())){
                String token = cookie.getValue();
                User user = userDao.findUserByToken(token);
                if(user != null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        //获取带用户信息的paginationDTO
        PaginationDTO paginationDTO =  questionService.getPaginationDTO(Currentpage, size);
        model.addAttribute("paginationDTO",paginationDTO);

        return "index";
    }
}
