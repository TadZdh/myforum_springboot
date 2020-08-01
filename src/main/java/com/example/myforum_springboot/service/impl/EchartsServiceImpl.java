package com.example.myforum_springboot.service.impl;

import com.example.myforum_springboot.domain.Category;
import com.example.myforum_springboot.mapper.EchartsMapper;
import com.example.myforum_springboot.service.EchartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EchartsServiceImpl implements EchartsService {

    @Autowired
    private EchartsMapper echartsMapper;

    @Override
    public List<Category> categoryList() {
        return echartsMapper.categoryList();
    }

    @Override
    public int inactiveUserCount() {
        return echartsMapper.inactiveUserCount();
    }

    @Override
    public int activeUserCount() {
        return echartsMapper.activeUserCount();
    }

    @Override
    public int newPostLastDay(int day) {
        return echartsMapper.newPostLastDay(day);
    }

    @Override
    public int normalUserCount() {
        return echartsMapper.normalUserCount();
    }

    @Override
    public int forbidUserCount() {
        return echartsMapper.forbidUserCount();
    }
}
