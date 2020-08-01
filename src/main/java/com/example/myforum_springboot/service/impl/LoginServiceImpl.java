package com.example.myforum_springboot.service.impl;

import com.example.myforum_springboot.mapper.LoginMapper;
import com.example.myforum_springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements UserDetailsService,LoginService{

    @Autowired
    private LoginMapper loginMapper;

    public com.example.myforum_springboot.domain.User getUser(String userName){
        return this.loginMapper.findByUserName(userName);
    }

    public List<com.example.myforum_springboot.domain.User> getAuthority(String userName){
        return this.loginMapper.findAuthoritiesByUsername(userName);
    }

    @Override
    public UserDetails getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails;
    }

    @Override
    public int userRegister(com.example.myforum_springboot.domain.User user) {
        com.example.myforum_springboot.domain.User u = loginMapper.findByUserName(user.getUserName());
        if(u!=null)
            return -1;
        user.setUserCreatedDate(new Date());
        user.setUserRole("user");
        user.setUserFlag(1);
        return this.loginMapper.userRegister(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.example.myforum_springboot.domain.User user = getUser(s);
        List<com.example.myforum_springboot.domain.User> authorities = getAuthority(s);
        List<SimpleGrantedAuthority> list = authorities.stream().map(authority ->
                new SimpleGrantedAuthority(authority.getUserRole())).collect(Collectors.toList());
        if(user!=null) {
            UserDetails userDetails = new User(user.getUserName(), user.getUserPassword(), list);
            user.setUserLoginDate(new Date());
            loginMapper.loginDate(user);
            return userDetails;
        }else{
            throw new UsernameNotFoundException("用户名不存在");
        }
    }
}
