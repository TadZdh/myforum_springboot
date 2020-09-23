package com.example.myforum_springboot.service;

import com.example.myforum_springboot.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface LoginService extends UserDetailsService {

    /**
     * 查询用户
     */
    User getUser(String userName);

    /**
     * 查询用户权限
     */
    List<User> getAuthority(String userName);

    /**
     * 获取用户登录信息
     */
    UserDetails getUser();

    /**
     *注册新用户
     */
    int userRegister(User user);

}
