package com.example.myforum_springboot.service;

import com.example.myforum_springboot.domain.Category;

import java.util.List;

public interface EchartsService {
    /**
     * 获取所有类别名称
     */
    List<Category> categoryList();

    /**
     * 非活跃用户数
     */
    int inactiveUserCount();

    /**
     * 活跃用户数
     */
    int activeUserCount();

    /**
     * 过去7天每天新帖子数
     */
    int newPostLastDay(int day);

    /**
     * 正常用户数
     */
    int normalUserCount();

    /**
     * 被禁言用户数
     */
    int forbidUserCount();
}
