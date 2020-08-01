package com.example.myforum_springboot.domain;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class Post {
    private int postId;
    @NotBlank(message = "标题不能为空！")
    private String postTitle;
    private String postBody;
    private Date postCreatedDate;
    private long postHits;
    private long postCommentCount;
    private User user;
    private Category category;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public Date getPostCreatedDate() {
        return postCreatedDate;
    }

    public void setPostCreatedDate(Date postCreatedDate) {
        this.postCreatedDate = postCreatedDate;
    }

    public long getPostHits() {
        return postHits;
    }

    public void setPostHits(long postHits) {
        this.postHits = postHits;
    }

    public long getPostCommentCount() {
        return postCommentCount;
    }

    public void setPostCommentCount(long postCommentCount) {
        this.postCommentCount = postCommentCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", postBody='" + postBody + '\'' +
                ", postCreatedDate=" + postCreatedDate +
                ", postHits=" + postHits +
                ", postCommentCount=" + postCommentCount +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}
