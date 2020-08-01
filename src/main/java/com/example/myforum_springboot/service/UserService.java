package com.example.myforum_springboot.service;

import com.example.myforum_springboot.domain.Comment;
import com.example.myforum_springboot.domain.Follow;
import com.example.myforum_springboot.domain.Post;
import com.example.myforum_springboot.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface UserService {

    /**
     * 用户发帖子
     */
    int postWrite(Post post,User user);

    /**
     * 回复帖子
     */
    int commentReply(Comment comment);

    /**
     * 删除自己的帖子
     */
    int postDelete(int postId);

    /**
     * 删除自己的回复
     */
    int commentDelete(int commentId);

    /**
     * 个人资料修改
     */
    int userUpdate(User user);

    /**
     * 上传头像
     */
    int fileUpload(HttpServletRequest request, MultipartFile photo,User user);

    /**
     * 查询自己发过的帖子
     */
    HashMap<String,Object> myPost(int currPage, String orderType, String queryPostTitle,String userName);

    /**
     * 查询自己的回复
     */
    HashMap<String,Object> myComment(int currPage, String orderType, String commentBody,String userName);

    /**
     * 修改密码
     */
    int pwdUpdate(User user);

    /**
     * 关注他人
     */
    int userFollow(int toId);

    /**
     * 解除关注他人
     */
    int userFollowDel(int toId);

    /**
     * 查询有无关注过此人
     */
    int hadFollow(int toId);

    /**
     * 查询关注数量
     */
    int followCount(int userId);

    /**
     * 查询粉丝数量
     */
    int fansCount(int userId);

    /**
     * 我的关注列表信息
     */
    HashMap<String,Object> followList(int currPage, String orderType, String queryUserName);

    /**
     * 我的粉丝列表信息
     */
    HashMap<String,Object> fansList(int currPage, String orderType, String queryUserName);
}
