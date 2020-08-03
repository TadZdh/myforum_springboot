package com.example.myforum_springboot.domain;

import java.util.Date;

public class Follow {
    private int followId;
    private String followFrom;
    private String followTo;
    private Date followDate;

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public String getFollowFrom() {
        return followFrom;
    }

    public void setFollowFrom(String followFrom) {
        this.followFrom = followFrom;
    }

    public String getFollowTo() {
        return followTo;
    }

    public void setFollowTo(String followTo) {
        this.followTo = followTo;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "followId=" + followId +
                ", followFrom='" + followFrom + '\'' +
                ", followTo='" + followTo + '\'' +
                ", followDate=" + followDate +
                '}';
    }
}

