package com.example.myforum_springboot.controller;

import com.example.myforum_springboot.domain.Category;
import com.example.myforum_springboot.domain.Echarts;
import com.example.myforum_springboot.service.EchartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private EchartsService echartsService;

    @PostMapping("/categoryEcharts")
    @ResponseBody
    public List<Echarts> categoryEcharts(){
        List<Category> categoryList = echartsService.categoryList();
        List<Echarts> list = new ArrayList<>();
        for(Category category : categoryList){
            list.add(new Echarts(category.getCategoryName(),category.getCategoryPostCount()));
        }
        return list;
    }

    @PostMapping("/userEcharts")
    @ResponseBody
    public List<Echarts> userEcharts(){
        int inactiveUserCount = echartsService.inactiveUserCount();
        int activeUserCount = echartsService.activeUserCount();
        List<Echarts> list = new ArrayList<>();
        list.add(new Echarts("非活跃用户",inactiveUserCount));
        list.add(new Echarts("活跃用户",activeUserCount));
        return list;
    }

    @PostMapping("/newPostEcharts")
    @ResponseBody
    public List<Echarts> newPostEcharts(){
        List<Echarts> list = new ArrayList<>();
        for(int i=7;i>0;i--){
            list.add(new Echarts(i+"天前",echartsService.newPostLastDay(i)));
        }
        return list;
    }

    @PostMapping("/forbidEcharts")
    @ResponseBody
    public List<Echarts> forbidEcharts(){
        int normalUserCount = echartsService.normalUserCount();
        int forbidUserCount = echartsService.forbidUserCount();
        List<Echarts> list = new ArrayList<>();
        list.add(new Echarts("被禁言用户",forbidUserCount));
        list.add(new Echarts("正常用户",normalUserCount));
        return list;
    }
}
