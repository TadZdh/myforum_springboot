package com.example.myforum_springboot.mapper;

import com.example.myforum_springboot.domain.Category;
import com.example.myforum_springboot.domain.Comment;
import com.example.myforum_springboot.domain.Page;
import com.example.myforum_springboot.domain.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PostMapper {
    /**
     * 查询某类别帖子数量
     */
    int postsCount(HashMap<String,Object> map);

    /**
     * 查询所有帖子类别名称
     */
    List<Category> getAllCategory();

    /**
     * 分页查询某类别别所有帖子
     */
    List<Post> getPostsByPage(HashMap<String,Object> map);

    /**
     * 查询该帖子的回复数量
     */
    long commentCount(int postId);

    /**
     * 帖子点击数+1
     */
    int hitsAdd(Post post);

    /**
     * 发帖子
     */
    int postSend(HashMap<String,Object> map);

    /**
     * 根据帖子类别查询其对应的Id
     */
    int getCategoryId(Category category);

    /**
     * 帖子详情
     */
    Post postDetails(Post post);

    /**
     * 查询某帖子的所有回复
     */
    List<Comment> getCommentsByPage(HashMap<String,Object> map);

    /**
     * 查询某帖子回复数量
     */
    int commentsCount(@Param("post") Post post,
                      @Param("userName") String userName);

}
