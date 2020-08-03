package com.example.myforum_springboot.service.impl;

import com.example.myforum_springboot.domain.*;
import com.example.myforum_springboot.mapper.LoginMapper;
import com.example.myforum_springboot.mapper.UserMapper;
import com.example.myforum_springboot.service.UserService;
import com.example.myforum_springboot.utils.FileUtils;
import com.example.myforum_springboot.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,readOnly=false)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int postWrite(Post post,User user) {
        post.setPostCreatedDate(new Date());
        Category category = userMapper.getCategoryByName(post.getCategory().getCategoryName());
        HashMap<String,Object> map = new HashMap<>();
        map.put("post",post);
        map.put("userName",user.getUserName());
        map.put("categoryId",category.getCategoryId());
        return userMapper.postWrite(map);
    }

    @Override
    public int commentReply(Comment comment) {
        comment.setCommentCreatedDate(new Date());
        int result = userMapper.commentReply(comment);
        if(result>0){
            Object obj = redisTemplate.opsForValue().get("commentCount_postId_"+comment.getPost().getPostId());
            if(obj!=null) {
                long count = Long.parseLong(obj.toString());
                redisTemplate.opsForValue().set("commentCount_postId_" + comment.getPost().getPostId(), count + 1L);
            }
        }
        return result;
    }

    @Override
    public int postDelete(int postId) {
        int commentCount = userMapper.commentDelCount(postId);
        if(commentCount>0){
            int commentResult = this.userMapper.withCommentDel(postId);
        }
        int delResult = this.userMapper.postDelete(postId);
        if(delResult>0){
            redisTemplate.delete("commentCount_postId_"+postId);
        }
        return delResult;
    }

    @Override
    public int commentDelete(int commentId) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("num",1);
        map.put("commentId",commentId);
        int result = userMapper.commentDelete(commentId);
        if(result>0){
            Set<String> keys = redisTemplate.keys("commentCount_postId_" + "*");
            redisTemplate.delete(keys);
        }
        return result;
    }

    @Override
    public int userUpdate(User user) {
        int result = userMapper.userUpdate(user);
        if(result>0)
            redisTemplate.delete("getUser_"+user.getUserName());
        return result;
    }

    @Override
    public int fileUpload(HttpServletRequest request, MultipartFile photo,User user) {
        String beforeImg = user.getUserPortrait();
        if(beforeImg!=null){
            FileUtils.deleteImgFile(beforeImg);
        }
        String img = FileUtils.uploadImgFile(request, photo);
        if(img.equals("非图片类型"))
            return -1;
        if(img.equals("图片为空"))
            return -2;
        user.setUserPortrait(img);
        int uploadResult = userMapper.fileUpload(user);
        redisTemplate.delete("getUser_"+user.getUserName());
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
        int result = userMapper.pwdUpdate(user);
        if(result>0)
            redisTemplate.delete("getUser_"+user.getUserName());
        return result;
    }

    @Override
    public int userFollow(String userName) {
        String myName = SecurityContextHolder.getContext().getAuthentication().getName();
        Follow follow = new Follow();
        follow.setFollowFrom(myName);
        follow.setFollowTo(userName);
        follow.setFollowDate(new Date());
        int result = userMapper.userFollow(follow);
/*        if(result>0) {
            redisTemplate.delete("getUser_" + myName);
            redisTemplate.delete("getUser_" + userName);
        }*/
        return result;
    }

    @Override
    public int userFollowDel(String userName) {
        String myName = SecurityContextHolder.getContext().getAuthentication().getName();
        Follow follow = new Follow();
        follow.setFollowFrom(myName);
        follow.setFollowTo(userName);
        int result = userMapper.userFollowDel(follow);
/*        if(result>0) {
            redisTemplate.delete("getUser_" + myName);
            redisTemplate.delete("getUser_" + userName);
        }*/
        return result;
    }

    @Override
    public int hadFollow(String userName) {
        String myName = SecurityContextHolder.getContext().getAuthentication().getName();
        Follow follow = new Follow();
        follow.setFollowFrom(myName);
        follow.setFollowTo(userName);
        return userMapper.hadFollow(follow);
    }

    @Override
    public int followCount(String userName) {
        return userMapper.followCount(userName);
    }

    @Override
    public int fansCount(String userName) {
        return userMapper.fansCount(userName);
    }

    @Override
    public HashMap<String, Object> followList(int currPage, String orderType, String queryUserName) {
        String myName = SecurityContextHolder.getContext().getAuthentication().getName();
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("userName",myName);
        map2.put("queryUserName",queryUserName);
        int totalCount = userMapper.followListCount(map2);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("orderType",orderType);
        map.put("queryUserName",queryUserName);
        map.put("userName",myName);
        HashMap<String,Object> map3 = new HashMap<>();
        List<User> users = userMapper.followList(map);
        map3.put("page",page);
        map3.put("user",users);
        return map3;
    }

    @Override
    public HashMap<String, Object> fansList(int currPage, String orderType, String queryUserName) {
        String myName = SecurityContextHolder.getContext().getAuthentication().getName();
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("userName",myName);
        map2.put("queryUserName",queryUserName);
        int totalCount = userMapper.fansListCount(map2);
        Page page = PageUtils.pageHandle(currPage,totalCount);
        map.put("start",page.getStart());
        map.put("size",page.getPageSize());
        map.put("orderType",orderType);
        map.put("queryUserName",queryUserName);
        map.put("userName",myName);
        HashMap<String,Object> map3 = new HashMap<>();
        List<User> users = userMapper.fansList(map);
        map3.put("page",page);
        map3.put("user",users);
        return map3;
    }

}
