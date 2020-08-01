package com.example.myforum_springboot.domain;

import java.util.Date;

public class Follow {
    private int followId;
    private int followFrom;
    private int followTo;
    private Date followDate;

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public int getFollowFrom() {
        return followFrom;
    }

    public void setFollowFrom(int followFrom) {
        this.followFrom = followFrom;
    }

    public int getFollowTo() {
        return followTo;
    }

    public void setFollowTo(int followTo) {
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
                ", followFrom=" + followFrom +
                ", followTo=" + followTo +
                ", followDate=" + followDate +
                '}';
    }
}

