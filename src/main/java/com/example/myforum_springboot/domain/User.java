package com.example.myforum_springboot.domain;

import java.util.Date;

public class User {
    private String userName;
    private String userPassword;
    private String userPortrait;
    private String userSignature;
    private String userEmail;
    private Date userCreatedDate;
    private Date userLoginDate;
    private String userRole;
    private int userFlag;
    private Follow follow;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(Date userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    public Date getUserLoginDate() {
        return userLoginDate;
    }

    public void setUserLoginDate(Date userLoginDate) {
        this.userLoginDate = userLoginDate;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(int userFlag) {
        this.userFlag = userFlag;
    }

    public Follow getFollow() {
        return follow;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }

    @Override
    public String toString() {
        return "User{" +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPortrait='" + userPortrait + '\'' +
                ", userSignature='" + userSignature + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userCreatedDate=" + userCreatedDate +
                ", userLoginDate=" + userLoginDate +
                ", userRole='" + userRole + '\'' +
                ", userFlag=" + userFlag +
                ", follow=" + follow +
                '}';
    }
}
