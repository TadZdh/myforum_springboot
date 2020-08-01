package com.example.myforum_springboot.mapper;

import com.example.myforum_springboot.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 用户发帖子
     */
    int postWrite(HashMap<String,Object> map);

    /**
     * 帖子数量+1
     */
    int postAdd(String categoryName);

    /**
     * 帖子数量-1
     */
    int postReduce(int id);

    /**
     * 根据类别名称查询其信息
     */
    Category getCategoryByName(String categoryName);

    /**
     * 用户回复帖子
     */
    int commentReply(Comment comment);

    /**
     * 帖子回复数量+1
     */
    int commentNumAdd(Comment comment);

    /**
     * 帖子回复数量-n
     */
    int commentNumReduce(HashMap<String,Object> map);

    /**
     * 批量删除自己的帖子
     */
    //int postDelete(HashMap<String,Object> map);

    /**
     * 删除自己的帖子
     */
    int postDelete(int postId);

    /**
     * 删除与被删帖子相关的回复
     */
    int withCommentDel(int postId);

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
    int fileUpload(User user);

    /**
     * 查询自己发过的帖子
     */
    List<Post> myPost(HashMap<String,Object> map);

    /**
     * 查询自己发过的帖子数量
     */
    int myPostCount(HashMap<String,Object> map);

    /**
     * 查询自己的回复
     */
    List<Comment> myComment(HashMap<String,Object> map);

    /**
     * 查询自己的回复数量
     */
    int myCommentCount(HashMap<String,Object> map);

    /**
     * 修改密码
     */
    int pwdUpdate(User user);

    /**
     * 关注他人
     */
    int userFollow(Follow follow);

    /**
     * 解除关注他人
     */
    int userFollowDel(Follow follow);

    /**
     * 查询有无关注过此人
     */
    int hadFollow(Follow follow);

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
    List<User> followList(HashMap<String,Object> map);

    /**
     * 我的关注列表数量
     */
    int followListCount(HashMap<String,Object> map);

    /**
     * 我的粉丝列表信息
     */
    List<User> fansList(HashMap<String,Object> map);

    /**
     * 我的粉丝列表数量
     */
    int fansListCount(HashMap<String,Object> map);
}
