package com.example.myforum_springboot.service.impl;

import com.example.myforum_springboot.domain.Category;
import com.example.myforum_springboot.mapper.EchartsMapper;
import com.example.myforum_springboot.service.EchartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
public class EchartsServiceImpl implements EchartsService {

    @Autowired
    private EchartsMapper echartsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Category> categoryList() {
        Object obj = redisTemplate.opsForValue().get("categoryList");
        if (obj != null) {
            return (List<Category>) obj;
        }
        List<Category> categoryList = echartsMapper.categoryList();
        redisTemplate.opsForValue().set("categoryList", categoryList);
        return categoryList;
    }

    @Override
    public int postCount(int categoryId) {
        return echartsMapper.postCount(categoryId);
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
