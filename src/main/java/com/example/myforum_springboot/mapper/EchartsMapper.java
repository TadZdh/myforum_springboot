package com.example.myforum_springboot.mapper;

import com.example.myforum_springboot.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface EchartsMapper {
    /**
     * 获取所有类别名称
     */
    List<Category> categoryList();

    /**
     * 查询每个分类下帖子数量
     */
    int postCount(int categoryId);

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
