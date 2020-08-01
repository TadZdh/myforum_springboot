package com.example.myforum_springboot.domain;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class Comment {
    private int commentId;
    @NotBlank(message = "回复内容不能为空！")
    private String commentBody;
    private Date commentCreatedDate;
    private User user;
    private Post post;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Date getCommentCreatedDate() {
        return commentCreatedDate;
    }

    public void setCommentCreatedDate(Date commentCreatedDate) {
        this.commentCreatedDate = commentCreatedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentBody='" + commentBody + '\'' +
                ", commentCreatedDate=" + commentCreatedDate +
                ", user=" + user +
                ", post=" + post +
                '}';
    }
}
