package com.example.myforum_springboot.domain;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class Category {
    private int categoryId;
    @NotBlank(message = "类名不能为空！")
    private String categoryName;
    private Date categoryCreatedDate;
    private long categoryPostCount;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCategoryCreatedDate() {
        return categoryCreatedDate;
    }

    public void setCategoryCreatedDate(Date categoryCreatedDate) {
        this.categoryCreatedDate = categoryCreatedDate;
    }

    public long getCategoryPostCount() {
        return categoryPostCount;
    }

    public void setCategoryPostCount(long categoryPostCount) {
        this.categoryPostCount = categoryPostCount;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryCreatedDate=" + categoryCreatedDate +
                ", categoryPostCount=" + categoryPostCount +
                '}';
    }
}
