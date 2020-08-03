package com.example.myforum_springboot.service.impl;

import com.example.myforum_springboot.domain.Category;
import com.example.myforum_springboot.domain.Page;
import com.example.myforum_springboot.domain.Post;
import com.example.myforum_springboot.domain.User;
import com.example.myforum_springboot.mapper.AdminMapper;
import com.example.myforum_springboot.mapper.LoginMapper;
import com.example.myforum_springboot.service.AdminService;
import com.example.myforum_springboot.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,readOnly=false)
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int postDelete(List<Integer> id) {
        int commentResult = adminMapper.commentDelete(id);
        int postResult = adminMapper.postDelete(id);
        if(postResult>0){
            Set<String> keys = redisTemplate.keys("commentCount_postId_" + "*");
            redisTemplate.delete(keys);
        }
        return postResult;
    }

    @Override
    public int commentDelete(List<Integer> id) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("num",id.size());
        map.put("id",id);
        int delResult = this.adminMapper.commentDelete(id);
        if(delResult>0){
            Set<String> keys = redisTemplate.keys("commentCount_postId_" + "*");
            redisTemplate.delete(keys);
        }
        return delResult;
    }

    @Override
    public int userForbid(String userName) {
        int result = adminMapper.userForbid(userName);
        if(result>0){
            redisTemplate.delete("getUser_" + userName);
        }
        return result;
    }

    @Override
    public int relieveForbid(String userName) {
        int result = adminMapper.relieveForbid(userName);
        if(result>0){
            redisTemplate.delete("getUser_" + userName);
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getUsersByPage(int currPage, String orderType, String queryUserName) {
        HashMap<String,Object> map = new HashMap<>();
        int totalCount = adminMapper.usersCount(queryUserName);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("orderType",orderType);
        map.put("queryUserName",queryUserName);
        List<User> list = adminMapper.getUserList(map);
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("page",page);
        map2.put("user",list);
        return map2;
    }

    @Override
    public int userDelete(String userName) {
        int follow = adminMapper.userFollowDel(userName);
        int result = adminMapper.userDelete(userName);
        if(result>0){
            redisTemplate.delete("getUser_" + userName);
        }
        return result;
    }

    @Override
    public int postCommentDel(String userName) {
        int commentCount = adminMapper.userDelCommentCount(userName);
        if(commentCount>0) {
            int commentResult = adminMapper.userDelCommentDel(userName);
            if(commentResult>0){
                Set<String> keys = redisTemplate.keys("commentCount_postId_" + "*");
                redisTemplate.delete(keys);
            }
        }
        int postCount = adminMapper.userDelPostCount(userName);
        if(postCount>0) {
            int postResult = adminMapper.userDelPostDel(userName);
            if(postResult>0){
                Set<String> keys = redisTemplate.keys("commentCount_postId_" + "*");
                redisTemplate.delete(keys);
            }
        }
        return 1;
    }

    @Override
    public HashMap<String, Object> getForbidByPage(int currPage, String orderType, String queryUserName) {
        HashMap<String,Object> map = new HashMap<>();
        int totalCount = adminMapper.forbidCount(queryUserName);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("orderType",orderType);
        map.put("queryUserName",queryUserName);
        List<User> list = adminMapper.getForbidList(map);
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("page",page);
        map2.put("user",list);
        return map2;
    }

    @Override
    public HashMap<String,Object> newPosts(int currPage, String orderType, String queryPostTitle) {
        HashMap<String,Object> map = new HashMap<>();
        int totalCount = adminMapper.newPostsCount(queryPostTitle);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("orderType",orderType);
        map.put("queryPostTitle",queryPostTitle);
        List<Post> list = adminMapper.newPosts(map);
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("page",page);
        map2.put("post",list);
        return map2;
    }

    @Override
    public HashMap<String, Object> getCategoryByPage(int currPage, String orderType, String queryCategoryName) {
        HashMap<String,Object> map = new HashMap<>();
        int totalCount = adminMapper.categoryCount(queryCategoryName);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("orderType",orderType);
        map.put("queryCategoryName",queryCategoryName);
        List<Category> list = adminMapper.getCategoryByPage(map);
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("page",page);
        map2.put("category",list);
        return map2;
    }

    @Override
    public long postCount(int categoryId) {
        return adminMapper.postCount(categoryId);
    }

    @Override
    public int categoryDelete(int id) {
        int commentResult = adminMapper.categoryDelCommentDel(id);
        int postResult = adminMapper.categoryDelPostDel(id);
        int categoryResult = adminMapper.categoryDelete(id);
        if(categoryResult>0){
            Set<String> keys = redisTemplate.keys("commentCount_postId_" + "*");
            redisTemplate.delete(keys);
            redisTemplate.delete("categoryList");
        }
        return categoryResult;
    }

    @Override
    public int categoryAdd(Category category) {
        int categoryExist = adminMapper.categoryExist(category);
        if(categoryExist>0){
            return -2;
        }
        category.setCategoryCreatedDate(new Date());
        int result = adminMapper.categoryAdd(category);
        if(result>0){
            redisTemplate.delete("categoryList");
        }
        return result;
    }
}
