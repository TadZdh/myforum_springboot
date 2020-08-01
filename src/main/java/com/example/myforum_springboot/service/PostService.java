package com.example.myforum_springboot.service;

import com.example.myforum_springboot.domain.Category;
import com.example.myforum_springboot.domain.Comment;
import com.example.myforum_springboot.domain.Post;

import java.util.HashMap;
import java.util.List;

public interface PostService {

    /**
     * 查询所有帖子类别名称
     */
    List<Category> getAllCategory();

    /**
     * 分页查询某类别别所有帖子
     */
    HashMap<String,Object> getPostsByPage(int currPage, Category category, String orderType, String queryPostTitle);

    /**
     * 发帖子
     */
    int postSend(int userId,Post post,Category category);

    /**
     * 帖子详情
     */
    Post postDetails(Post post);

    /**
     * 查询某帖子的所有回复
     */
    HashMap<String,Object> getCommentsByPage(int currPage, Post post, int userId);
}
