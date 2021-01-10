package com.example.myforum_springboot.mapper;

import com.example.myforum_springboot.domain.Category;
import com.example.myforum_springboot.domain.Post;
import com.example.myforum_springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AdminMapper {

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
     * 查询所有用户信息
     */
    List<User> getUserList(HashMap<String, Object> map);

    /**
     * 查询所有用户数量
     */
    int usersCount(String queryUserName);

    /**
     * 注销用户
     */
    int userDelete(String userName);

    /**
     * 删除被注销用户的关注信息
     */
    int userFollowDel(String userName);

    /**
     * 查询被注销用户发布的帖子的数量
     */
    int userDelPostCount(String userName);

    /**
     * 删除被注销用户发布的帖子
     */
    int userDelPostDel(String userName);

    /**
     * 查询被注销用户的所有回复的数量
     */
    int userDelCommentCount(String userName);

    /**
     * 删除被注销用户的所有回复
     */
    int userDelCommentDel(String userName);

    /**
     * 查询所有的禁言用户
     */
    List<User> getForbidList(HashMap<String, Object> map);

    /**
     * 查询所有的禁言用户的数量
     */
    int forbidCount(String queryUserName);

    /**
     * 近七天发布的新帖子
     */
    List<Post> newPosts(HashMap<String, Object> map);

    /**
     * 近七天发布的新帖子的数量
     */
    int newPostsCount(String queryPostTitle);

    /**
     * 所有类别信息
     */
    List<Category> getCategoryByPage(HashMap<String, Object> map);

    /**
     * 查询每个分类下的帖子数量
     */
    long postCount(int categoryId);

    /**
     * 所有类别数量
     */
    int categoryCount(String queryCategoryName);

    /**
     * 删除分类
     */
    int categoryDelete(int id);

    /**
     * 删除被删除分类的帖子
     */
    int categoryDelPostDel(int id);

    /**
     * 删除被删除分类的帖子的回复
     */
    int categoryDelCommentDel(int id);

    /**
     * 新增分类
     */
    int categoryAdd(Category category);

    /**
     * 查询有无该分类
     */
    int categoryExist(Category category);
}
