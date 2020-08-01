package com.example.myforum_springboot.controller;

import com.example.myforum_springboot.domain.*;
import com.example.myforum_springboot.service.LoginService;
import com.example.myforum_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/postWrite")
    @ResponseBody
    public int postWrite(HttpServletRequest request,@RequestBody HashMap<String,Object> map){
        Post post = new Post();
        String postTitle = (String) map.get("postTitle");
        String postBody = (String) map.get("postBody");
        String categoryName = (String) map.get("categoryName");
        post.setPostTitle(postTitle);
        post.setPostBody(postBody);
        Category category = new Category();
        category.setCategoryName(categoryName);
        post.setCategory(category);
        String code = (String) map.get("code");
        if(code.equals(request.getSession().getAttribute("code"))) {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = loginService.getUser(userName);
            return userService.postWrite(post,user);
        }
        return -1;
    }

    @PostMapping("/reply")
    @ResponseBody
    public int commentReply(HttpServletRequest request,@RequestBody HashMap<String,Object> map){
        User user = new User();
        user.setUserId((Integer) map.get("userId"));
        Post post = new Post();
        post.setPostId((Integer) map.get("postId"));
        Comment comment = new Comment();
        comment.setCommentBody((String) map.get("commentBody"));
        comment.setUser(user);
        comment.setPost(post);
        String code = (String) map.get("code");
        if(code.equals(request.getSession().getAttribute("code")))
            return userService.commentReply(comment);
        return -1;
    }

    @PostMapping("/postDel")
    @ResponseBody
    public int myPostDel(@RequestBody int id){
        return userService.postDelete(id);
    }

    @PostMapping("/commentDel")
    @ResponseBody
    public int myCommentDel(@RequestBody int id){
        return userService.commentDelete(id);
    }

    @GetMapping("/perInfo")
    public String getPerInfo(Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = loginService.getUser(userName);
        model.addAttribute("user",user);
        int follows = userService.followCount(user.getUserId());
        int fans = userService.fansCount(user.getUserId());
        model.addAttribute("follows",follows);
        model.addAttribute("fans",fans);
        return "settings/perInfo";
    }

    @PostMapping("/userUpdate")
    @ResponseBody
    public int userUpdate(@RequestBody User user){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        user.setUserName(userName);
        return userService.userUpdate(user);
    }

    @PostMapping("/fileUpload")
    @ResponseBody
    public int fileUpload(@RequestParam( value="files",required=false) MultipartFile photo, HttpServletRequest request){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = loginService.getUser(userName);
        return userService.fileUpload(request,photo,user);
    }

    @GetMapping("/perPost")
    public String getPerPost(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="orderType",defaultValue="desc",required=false) String orderType,
            @RequestParam(value="queryPostTitle",required=false) String queryPostTitle,
            Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = new User();
        user.setUserName(userName);
        HashMap<String,Object> map = userService.myPost(currPage,orderType,queryPostTitle,userName);
        List<Post> posts = (List<Post>) map.get("post");
        Page page = (Page) map.get("page");
        model.addAttribute("posts",posts);
        model.addAttribute("page",page);
        model.addAttribute("orderType",orderType);
        model.addAttribute("beforeCurrPage",currPage);
        model.addAttribute("queryPostTitle",queryPostTitle);
        return "settings/perPost";
    }

    @GetMapping("/perComment")
    public String getPerComment(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="orderType",defaultValue="desc",required=false) String orderType,
            @RequestParam(value="queryCommentBody",required=false) String queryCommentBody,
            Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = new User();
        user.setUserName(userName);
        HashMap<String,Object> map = userService.myComment(currPage,orderType,queryCommentBody,userName);
        List<Comment> comments = (List<Comment>) map.get("comment");
        Page page = (Page) map.get("page");
        model.addAttribute("comments",comments);
        model.addAttribute("page",page);
        model.addAttribute("orderType",orderType);
        model.addAttribute("beforeCurrPage",currPage);
        model.addAttribute("queryCommentBody",queryCommentBody);
        return "settings/perComment";
    }

    @GetMapping("/otherInfo")
    public String otherInfo(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="orderType",defaultValue="asc",required=false) String orderType,
            @RequestParam(value="queryPostTitle",required=false) String queryPostTitle,
            String userName,
            Model model){
        User user = loginService.getUser(userName);
        model.addAttribute("user",user);
        HashMap<String,Object> map = userService.myPost(currPage,orderType,queryPostTitle,userName);
        List<Post> posts = (List<Post>) map.get("post");
        Page page = (Page) map.get("page");
        model.addAttribute("posts",posts);
        model.addAttribute("page",page);
        model.addAttribute("orderType",orderType);
        model.addAttribute("beforeCurrPage",currPage);
        model.addAttribute("queryPostTitle",queryPostTitle);
        String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loginName", loginName);
        int follows = userService.followCount(user.getUserId());
        int fans = userService.fansCount(user.getUserId());
        model.addAttribute("follows",follows);
        model.addAttribute("fans",fans);
        int hadFollow = userService.hadFollow(user.getUserId());
        model.addAttribute("hadFollow",hadFollow);
        return "page/otherInfo";
    }

    @PostMapping("/pwdUpdate")
    @ResponseBody
    public int pwdUpdate(@RequestBody User user){
        return userService.pwdUpdate(user);
    }

    @PostMapping("/userFollow")
    @ResponseBody
    public int userFollow(@RequestBody int toId){
        return userService.userFollow(toId);
    }

    @PostMapping("/userFollowDel")
    @ResponseBody
    public int userFollowDel(@RequestBody int toId){
        return userService.userFollowDel(toId);
    }

    @GetMapping("/myFollow")
    public String myFollow(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="orderType",defaultValue="desc",required=false) String orderType,
            @RequestParam(value="queryUserName",required=false) String queryUserName,
            Model model){
        HashMap<String,Object> map = userService.followList(currPage,orderType,queryUserName);
        List<User> users = (List<User>) map.get("user");
        Page page = (Page) map.get("page");
        model.addAttribute("users",users);
        model.addAttribute("page",page);
        model.addAttribute("orderType",orderType);
        model.addAttribute("beforeCurrPage",currPage);
        model.addAttribute("queryUserName",queryUserName);
        return "settings/myFollow";
    }

    @GetMapping("/myFans")
    public String myFans(
            @RequestParam(value="currPage",defaultValue="1",required=false) int currPage,
            @RequestParam(value="orderType",defaultValue="desc",required=false) String orderType,
            @RequestParam(value="queryUserName",required=false) String queryUserName,
            Model model){
        HashMap<String,Object> map = userService.fansList(currPage,orderType,queryUserName);
        List<User> users = (List<User>) map.get("user");
        Page page = (Page) map.get("page");
        model.addAttribute("users",users);
        model.addAttribute("page",page);
        model.addAttribute("orderType",orderType);
        model.addAttribute("beforeCurrPage",currPage);
        model.addAttribute("queryUserName",queryUserName);
        return "settings/myFans";
    }
}
