package com.example.myforum_springboot.mapper;

import com.example.myforum_springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginMapper {

    /**
     * 查询用户
     */
    User findByUserName(String userName);

    /**
     * 查询用户权限
     */
    List<User> findAuthoritiesByUsername(String userName);

    /**
     * 注册新用户
     */
    int userRegister(User user);

    /**
     * 更新用户登录时间
     */
    int loginDate(User user);
}
