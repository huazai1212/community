package com.huazai.community.controller;

import com.huazai.community.dto.AccessTokenDTO;
import com.huazai.community.dto.GithubUser;
import com.huazai.community.model.User;
import com.huazai.community.provider.GithubProvider;
import com.huazai.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")//请求路径
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,

                           HttpServletResponse response){//获取请求地址栏传回的参数

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser =  githubProvider.getUser(accessToken);//获取用户信息

        if (githubUser != null  && githubUser.getId() != null){
            //登陆成功
            //设置user信息
            User user = new User();
            String token = UUID.randomUUID().toString();//代替session
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setBio(githubUser.getBio());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            //判断是否有用户，如果有则更新，如果没有则新增
            userService.createOrUpdateUser(user);

            response.addCookie(new Cookie("token",token));//将随机生成的token放到cookie

            //request.getSession().setAttribute("user",githubUser);//将用户信息放到session中
            return "redirect:/";//返回界面(重定向)
        }else{
            return "redirect:/";//返回界面(重定向)
            //登陆失败

        }

    }
}
