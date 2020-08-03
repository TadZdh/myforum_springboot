package com.example.myforum_springboot.service;

import com.example.myforum_springboot.domain.Category;
import com.example.myforum_springboot.domain.Post;
import com.example.myforum_springboot.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.List;

public interface AdminService {

    /**
     * 批量删除帖子
     */
    int postDelete(List<Integer> id);

    /**
     * 批量删除回复
     */
    int commentDelete(List<Integer> id);

    /**
     * 给用户禁言
     */
    int userForbid(String userName);

    /**
     * 解除禁言
     */
    int relieveForbid(String userName);

    /**
     * 查询所有用户
     */
    HashMap<String,Object> getUsersByPage(int currPage, String orderType, String queryUserName);

    /**
     * 注销用户
     */
    int userDelete(String userName);

    /**
     * 删除被注销用户发布的帖子和回复
     */
    int postCommentDel(String userName);

    /**
     * 查询所有禁言的用户
     */
    HashMap<String,Object> getForbidByPage(int currPage, String orderType, String queryUserName);

    /**
     * 近七天发布的新帖子
     */
    HashMap<String,Object> newPosts(int currPage, String orderType, String queryPostTitle);

    /**
     * 所有类别信息
     */
    HashMap<String,Object> getCategoryByPage(int currPage, String orderType, String queryCategoryName);

    /**
     * 查询每个分类下的帖子数量
     */
    long postCount(int categoryId);

    /**
     * 删除分类
     */
    int categoryDelete(int id);

    /**
     * 新增分类
     */
    int categoryAdd(Category category);
}
