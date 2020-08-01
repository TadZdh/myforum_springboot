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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public int postDelete(List<Integer> id) {
        int commentResult = adminMapper.commentDelete(id);
        int postResult = adminMapper.postDelete(id);
        try {
            if (commentResult <= 0 && postResult <= 0) {
                throw new Exception();
            }
        }catch (Exception e){
            return 0;
        }
        return postResult;
    }

    @Override
    public int postNumReduce(int num, String categoryName) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("num",num);
        map.put("categoryName",categoryName);
        return adminMapper.postNumReduce(map);
    }

    @Override
    public int commentDelete(List<Integer> id) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("num",id.size());
        map.put("id",id);
        int reduceResult = this.adminMapper.commentNumReduce(map);
        int delResult = this.adminMapper.commentDelete(id);
        try {
            if (reduceResult <= 0 && delResult <= 0) {
                throw new Exception();
            }
        }catch (Exception e){
            return 0;
        }
        return delResult;
    }

    @Override
    public int userForbid(int id) {
        return this.adminMapper.userForbid(id);
    }

    @Override
    public int relieveForbid(int id) {
        return this.adminMapper.relieveForbid(id);
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
    public int userDelete(int id) {
        return adminMapper.userDelete(id);
    }

    @Override
    public int postCommentDel(int id) {
        int reduceResult = adminMapper.userDelPostReduce(id);
        int commentResult = adminMapper.userDelCommentDel(id);
        int postResult = adminMapper.userDelPostDel(id);
        try {
            if (postResult<=0&&commentResult<=0&&reduceResult<=0) {
                throw new Exception();
            }
        }catch (Exception e){
            return 0;
        }
        return postResult;
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
    public int categoryDelete(int id) {
        int commentResult = adminMapper.categoryDelCommentDel(id);
        int postResult = adminMapper.categoryDelPostDel(id);
        int categoryResult = adminMapper.categoryDelete(id);
        try {
            if (postResult<=0&&commentResult<=0&&categoryResult<=0) {
                throw new Exception();
            }
        }catch (Exception e){
            return 0;
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
        return this.adminMapper.categoryAdd(category);
    }
}
