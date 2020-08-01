package com.example.myforum_springboot.service.impl;

import com.example.myforum_springboot.domain.Category;
import com.example.myforum_springboot.domain.Comment;
import com.example.myforum_springboot.domain.Page;
import com.example.myforum_springboot.domain.Post;
import com.example.myforum_springboot.mapper.PostMapper;
import com.example.myforum_springboot.service.PostService;
import com.example.myforum_springboot.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,readOnly=false)
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Category> getAllCategory() {
        return this.postMapper.getAllCategory();
    }

    @Override
    public HashMap<String,Object> getPostsByPage(int currPage, Category category, String orderType, String queryPostTitle) {
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("category",category);
        map2.put("queryPostTitle",queryPostTitle);
        int totalCount = postMapper.postsCount(map2);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("categoryName",category.getCategoryName());
        map.put("orderType",orderType);
        map.put("queryPostTitle",queryPostTitle);
        List<Post> list = postMapper.getPostsByPage(map);
        HashMap<String,Object> map3 = new HashMap<>();
        map3.put("page",page);
        map3.put("post",list);
        return map3;
    }

    @Override
    public int postSend(int userId,Post post,Category category) {
        HashMap<String,Object> map = new HashMap<>();
        int categoryId = postMapper.getCategoryId(category);
        map.put("userId",userId);
        map.put("post",post);
        map.put("categoryId",categoryId);
        return this.postMapper.postSend(map);
    }

    @Override
    public Post postDetails(Post post) {
        postMapper.hitsAdd(post);
        return this.postMapper.postDetails(post);
    }

    @Override
    public HashMap<String, Object> getCommentsByPage(int currPage, Post post, int userId) {
        HashMap<String,Object> map = new HashMap<>();
        int totalCount = postMapper.commentsCount(post,userId);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("post",post);
        map.put("userId",userId);
        List<Comment> list = postMapper.getCommentsByPage(map);
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("page",page);
        map2.put("comment",list);
        return map2;
    }
}
