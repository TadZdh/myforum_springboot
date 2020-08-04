package com.example.myforum_springboot.utils;

import com.example.myforum_springboot.domain.Page;

public class PageUtils {
    private static final int pageSize = 8;

    public static Page pageHandle(int currPage,int totalCount){
        Page page = new Page();
        page.setCurrPage(currPage);
        page.setPageSize(pageSize);
        double totals = totalCount;
        Double num = Math.ceil(totals/pageSize);
        page.setTotalPage(num.intValue());
        page.setTotalCount(totalCount);
        page.setStart((currPage-1)*pageSize);
        return page;
    }
}
