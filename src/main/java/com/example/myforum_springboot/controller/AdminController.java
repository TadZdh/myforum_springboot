package com.example.myforum_springboot.controller;

import com.example.myforum_springboot.domain.*;
import com.example.myforum_springboot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/postDelete")
    @ResponseBody
    public int postDel(@RequestBody HashMap<String,Object> s){
        List<Integer> deleteId = (List<Integer>) s.get("deleteId");
        String categoryName = (String) s.get("categoryName");
        if(deleteId!=null&&deleteId.size()!=0){
            return adminService.postDelete(deleteId);
        }
        return 0;
    }

    @PostMapping("/commentDelete")
    @ResponseBody
    public int commentDel(@RequestBody List<Integer> deleteId){
        if(deleteId!=null&&deleteId.size()!=0){
            return adminService.commentDelete(deleteId);
        }
        return 0;
    }

    @PostMapping("/userForbid")
    @ResponseBody
    public int userForbid(@RequestBody Object userName){
        if(userName.equals(""))
            return 0;
        return adminService.userForbid((String) userName);
    }

    @PostMapping("/relieveForbid")
    @ResponseBody
    public int relieveForbid(@RequestBody Object userName){
        if(userName.equals(""))
            return 0;
        return adminService.relieveForbid((String) userName);
    }

    @GetMapping("/userList")
    public String userList(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="orderType",defaultValue="desc",required=false) String orderType,
            @RequestParam(value="queryUserName",required=false) String queryUserName,
            Model model){
        HashMap<String,Object> map = adminService.getUsersByPage(currPage,orderType,queryUserName);
        List<User> users = (List<User>) map.get("user");
        Page page = (Page) map.get("page");
        model.addAttribute("users",users);
        model.addAttribute("page",page);
        model.addAttribute("beforeCurrPage",currPage);
        model.addAttribute("orderType",orderType);
        model.addAttribute("queryUserName",queryUserName);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("userName",userName);
        return "manage/userList";
    }

    @PostMapping("/userDelete")
    @ResponseBody
    public int userDelete(@RequestBody Object userName){
        if(userName.equals(""))
            return 0;
        int result = adminService.postCommentDel((String) userName);
        if (result <= 0) {
            return 0;
        }
        return adminService.userDelete((String) userName);
    }

    @GetMapping("/forbidList")
    public String forbidList(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="orderType",defaultValue="desc",required=false) String orderType,
            @RequestParam(value="queryUserName",required=false) String queryUserName,
            Model model){
        HashMap<String,Object> map = adminService.getForbidByPage(currPage,orderType,queryUserName);
        List<User> users = (List<User>) map.get("user");
        Page page = (Page) map.get("page");
        model.addAttribute("users",users);
        model.addAttribute("page",page);
        model.addAttribute("beforeCurrPage",currPage);
        model.addAttribute("orderType",orderType);
        model.addAttribute("queryUserName",queryUserName);
        return "manage/forbidList";
    }

    @GetMapping("/newPostList")
    public String newPostList(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="orderType",defaultValue="desc",required=false) String orderType,
            @RequestParam(value="queryPostTitle",required=false) String queryPostTitle,
            Model model){
        HashMap<String,Object> map = adminService.newPosts(currPage,orderType,queryPostTitle);
        List<Post> posts = (List<Post>) map.get("post");
        Page page = (Page) map.get("page");
        model.addAttribute("posts",posts);
        model.addAttribute("page",page);
        model.addAttribute("beforeCurrPage",currPage);
        model.addAttribute("orderType",orderType);
        model.addAttribute("queryPostTitle",queryPostTitle);
        return "manage/newPostList";
    }

    @GetMapping("/categoryList")
    public String categoryList(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="orderType",defaultValue="desc",required=false) String orderType,
            @RequestParam(value="queryCategoryName",required=false) String queryCategoryName,
            Model model){
        HashMap<String,Object> map = adminService.getCategoryByPage(currPage,orderType,queryCategoryName);
        List<Category> categorys = (List<Category>) map.get("category");
        for(Category category : categorys){
            category.setCategoryPostCount(adminService.postCount(category.getCategoryId()));
        }
        Page page = (Page) map.get("page");
        model.addAttribute("categorys",categorys);
        model.addAttribute("page",page);
        model.addAttribute("beforeCurrPage",currPage);
        model.addAttribute("orderType",orderType);
        model.addAttribute("queryCategoryName",queryCategoryName);
        return "manage/categoryList";
    }

    @PostMapping("/categoryDelete")
    @ResponseBody
    public int categoryDelete(@RequestBody int id){
        if(id<=0)
            return 0;
        return adminService.categoryDelete(id);
    }

    @PostMapping("/categoryAdd")
    @ResponseBody
    public int categoryAdd(@RequestBody @Valid Category category, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return -1;
        }
        if(category != null)
            return adminService.categoryAdd(category);
        return 0;
    }
}
