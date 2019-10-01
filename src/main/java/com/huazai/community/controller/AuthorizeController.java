package com.huazai.community.controller;

import com.huazai.community.dto.AccessTokenDTO;
import com.huazai.community.dto.GithubUser;
import com.huazai.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")//请求路径
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){//获取请求地址栏传回的参数

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user =  githubProvider.getUser(accessToken);
        System.out.println("=====================");
        System.out.println(user.getName());
        System.out.println(user.getId());
        return "index";//返回界面
    }
}
