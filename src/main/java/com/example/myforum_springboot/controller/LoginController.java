package com.example.myforum_springboot.controller;

import com.example.myforum_springboot.domain.User;
import com.example.myforum_springboot.service.LoginService;
import com.example.myforum_springboot.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/userRegister")
    @ResponseBody
    public int userRegister(HttpServletRequest request,@RequestBody HashMap<String,Object> map){
        User user = new User();
        String userName = (String) map.get("userName");
        String userPassword = (String) map.get("userPassword");
        String userEmail = (String) map.get("userEmail");
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setUserEmail(userEmail);
        String code = (String) map.get("code");
        if(code.equals(request.getSession().getAttribute("code")))
            return loginService.userRegister(user);
        return -2;
    }

    @RequestMapping("/toGetCode")
    public void toGetCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expire",0);
        CodeUtils.getCode(request,response);
    }
}
