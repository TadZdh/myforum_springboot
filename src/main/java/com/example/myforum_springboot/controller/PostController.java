package com.example.myforum_springboot.controller;

import com.example.myforum_springboot.domain.*;
import com.example.myforum_springboot.service.LoginService;
import com.example.myforum_springboot.service.PostService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/home")
    public String toHomePage(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="categoryName",defaultValue="java",required=false) String categoryName,
            @RequestParam(value="orderType",defaultValue="desc",required=false) String orderType,
            @RequestParam(value="queryPostTitle",required=false) String queryPostTitle,
            Model model){
        Category category = new Category();
        category.setCategoryName(categoryName);
        HashMap<String,Object> map = postService.getPostsByPage(currPage,category,orderType,queryPostTitle);
        List<Post> posts = (List<Post>) map.get("post");
        for(Post post: posts){
            post.setPostCommentCount(postService.commentCount(post.getPostId()));
        }
        Page page = (Page) map.get("page");
        model.addAttribute("posts",posts);
        List<Category> categorys = postService.getAllCategory();
        model.addAttribute("categorys",categorys);
        model.addAttribute("choose",categoryName);
        model.addAttribute("page",page);
        model.addAttribute("beforeCurrPage",currPage);
        model.addAttribute("orderType",orderType);
        if(queryPostTitle!=null&& !queryPostTitle.equals(""))
            model.addAttribute("queryPostTitle",queryPostTitle);
        else
            model.addAttribute("queryPostTitle",null);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = loginService.getUser(userName);
            model.addAttribute("user", user);
            return "home/home";
        }else{
            return "home/home";
        }
    }

    @GetMapping("/details")
    public String toPostDetails(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="homeCurrPage",defaultValue="1",required=false) int homeCurrPage,
            @RequestParam(value="orderType",defaultValue="desc",required=false) String orderType,
            @RequestParam(value="queryPostTitle",required=false) String queryPostTitle,
            @RequestParam(value="onlyWatch",defaultValue="0",required=false) int onlyWatch,
            Post post,
            Model model){
        Post post2 = postService.postDetails(post);
        model.addAttribute("post",post2);
        HashMap<String,Object> map = new HashMap<>();
        if(onlyWatch==1)
            map = postService.getCommentsByPage(currPage,post,post2.getUser().getUserName());
        else
            map = postService.getCommentsByPage(currPage,post,null);
        List<Comment> comments = (List<Comment>) map.get("comment");
        Page page = (Page) map.get("page");
        model.addAttribute("comments",comments);
        model.addAttribute("page",page);
        model.addAttribute("homeCurrPage",homeCurrPage);
        model.addAttribute("orderType",orderType);
        model.addAttribute("onlyWatch",onlyWatch);
        if(queryPostTitle!=null&& !queryPostTitle.equals(""))
            model.addAttribute("queryPostTitle",queryPostTitle);
        else
            model.addAttribute("queryPostTitle",null);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!userName.equals("anonymousUser")) {
            User user = loginService.getUser(userName);
            model.addAttribute("user", user);
            return "home/post";
        }else{
            return "home/post";
        }
    }
}
