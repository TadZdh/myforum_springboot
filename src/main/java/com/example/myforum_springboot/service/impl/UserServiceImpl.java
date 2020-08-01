package com.example.myforum_springboot.service.impl;

import com.example.myforum_springboot.domain.*;
import com.example.myforum_springboot.mapper.LoginMapper;
import com.example.myforum_springboot.mapper.UserMapper;
import com.example.myforum_springboot.service.UserService;
import com.example.myforum_springboot.utils.FileUtils;
import com.example.myforum_springboot.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public int postWrite(Post post,User user) {
        post.setPostCreatedDate(new Date());
        Category category = userMapper.getCategoryByName(post.getCategory().getCategoryName());
        HashMap<String,Object> map = new HashMap<>();
        map.put("post",post);
        map.put("userId",user.getUserId());
        map.put("categoryId",category.getCategoryId());
        int addResult = userMapper.postAdd(post.getCategory().getCategoryName());
        int writeResult = userMapper.postWrite(map);
        try {
            if (addResult <= 0 && writeResult <= 0) {
                throw new Exception();
            }
        }catch (Exception e){
            return 0;
        }
        return writeResult;
    }

    @Override
    public int commentReply(Comment comment) {
        comment.setCommentCreatedDate(new Date());
        int replyResult = userMapper.commentReply(comment);
        int addResult = userMapper.commentNumAdd(comment);
        try {
            if (replyResult <= 0 && addResult <= 0) {
                throw new Exception();
            }
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int postDelete(int postId) {
        int commentResult = this.userMapper.withCommentDel(postId);
        int reduceResult = this.userMapper.postReduce(postId);
        int delResult = this.userMapper.postDelete(postId);
        try {
            if (commentResult <= 0 && delResult <= 0 && reduceResult<=0) {
                throw new Exception();
            }
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int commentDelete(int commentId) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("num",1);
        map.put("commentId",commentId);
        int reduceResult = this.userMapper.commentNumReduce(map);
        int delResult = this.userMapper.commentDelete(commentId);
        try {
            if (reduceResult <= 0 && delResult <= 0) {
                throw new Exception();
            }
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int userUpdate(User user) {
        return this.userMapper.userUpdate(user);
    }

    @Override
    public int fileUpload(HttpServletRequest request, MultipartFile photo,User user) {
        String beforeImg = user.getUserPortrait();
        boolean flag = true;
        if(beforeImg!=null){
            flag = FileUtils.deleteImgFile(beforeImg);
        }
        String img = FileUtils.uploadImgFile(request, photo);
        if(img.equals("非图片类型"))
            return -1;
        if(img.equals("图片为空"))
            return -2;
        user.setUserPortrait(img);
        int uploadResult = userMapper.fileUpload(user);
        try {
            if (!flag && uploadResult <= 0) {
                throw new Exception();
            }
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public HashMap<String,Object> myPost(int currPage, String orderType, String queryPostTitle,String userName) {
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("userName",userName);
        map2.put("queryPostTitle",queryPostTitle);
        int totalCount = userMapper.myPostCount(map2);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("orderType",orderType);
        map.put("queryPostTitle",queryPostTitle);
        map.put("userName",userName);
        HashMap<String,Object> map3 = new HashMap<>();
        List<Post> posts = userMapper.myPost(map);
        map3.put("page",page);
        map3.put("post",posts);
        return map3;
    }

    @Override
    public HashMap<String,Object> myComment(int currPage, String orderType, String commentBody,String userName) {
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("userName",userName);
        map2.put("commentBody",commentBody);
        int totalCount = userMapper.myCommentCount(map2);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("orderType",orderType);
        map.put("commentBody",commentBody);
        map.put("userName",userName);
        HashMap<String,Object> map3 = new HashMap<>();
        List<Comment> comments = userMapper.myComment(map);
        map3.put("page",page);
        map3.put("comment",comments);
        return map3;
    }

    @Override
    public int pwdUpdate(User user) {
        user.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        return userMapper.pwdUpdate(user);
    }

    @Override
    public int userFollow(int toId) {
        String myName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = loginMapper.findByUserName(myName);
        Follow follow = new Follow();
        follow.setFollowFrom(user.getUserId());
        follow.setFollowTo(toId);
        follow.setFollowDate(new Date());
        return userMapper.userFollow(follow);
    }

    @Override
    public int userFollowDel(int toId) {
        String myName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = loginMapper.findByUserName(myName);
        Follow follow = new Follow();
        follow.setFollowFrom(user.getUserId());
        follow.setFollowTo(toId);
        return userMapper.userFollowDel(follow);
    }

    @Override
    public int hadFollow(int toId) {
        String myName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = loginMapper.findByUserName(myName);
        Follow follow = new Follow();
        follow.setFollowFrom(user.getUserId());
        follow.setFollowTo(toId);
        return userMapper.hadFollow(follow);
    }

    @Override
    public int followCount(int userId) {
        return userMapper.followCount(userId);
    }

    @Override
    public int fansCount(int userId) {
        return userMapper.fansCount(userId);
    }

    @Override
    public HashMap<String, Object> followList(int currPage, String orderType, String queryUserName) {
        String myName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = loginMapper.findByUserName(myName);
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("userId",user.getUserId());
        map2.put("queryUserName",queryUserName);
        int totalCount = userMapper.followListCount(map2);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("orderType",orderType);
        map.put("queryUserName",queryUserName);
        map.put("userId",user.getUserId());
        HashMap<String,Object> map3 = new HashMap<>();
        List<User> users = userMapper.followList(map);
        map3.put("page",page);
        map3.put("user",users);
        return map3;
    }

    @Override
    public HashMap<String, Object> fansList(int currPage, String orderType, String queryUserName) {
        String myName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = loginMapper.findByUserName(myName);
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("userId",user.getUserId());
        map2.put("queryUserName",queryUserName);
        int totalCount = userMapper.fansListCount(map2);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("orderType",orderType);
        map.put("queryUserName",queryUserName);
        map.put("userId",user.getUserId());
        HashMap<String,Object> map3 = new HashMap<>();
        List<User> users = userMapper.fansList(map);
        map3.put("page",page);
        map3.put("user",users);
        return map3;
    }

}
