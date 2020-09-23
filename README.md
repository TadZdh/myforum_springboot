# myforum_springboot  

* ## 该程序是一个基于Spring Boot开发的《程序猿小论坛》，主要业务是用户可以在此系统发布新帖子进行交流、交友

* ## 系统主要运用了Spring Boot+mybatis技术进行开发，着重在java web后端的开发。页面使用了Thymeleaf模板+Bootstrap框架，表单提交进行了表单验证，使用ajax异步提交，登录和权限验证使用了Spring Security安全框架，并且密码进行加密处理和保存，数据的操作使用了Spring事务管理和Redis缓存，并且能够分页显示，数据库为MySql数据库

* ## 系统分为两个用户，一个是管理员，另一个是用户，管理员拥有特殊权限，同时管理员也具有用户的权限  

  * ### 管理员功能：  
  
    删除用户发布的帖子  
    将用户禁言、解除禁言  
    查看总览（帖子图表、用户图表）  
    审核7天内新帖子  
    增删帖子分类  
    注销用户  
    
  * ### 普通用户功能： 
  
    登录、注册  
    发布新帖子  
    发布回复消息  
    删除自己发布的帖子、回复  
    查看其他用户基础信息、帖子信息  
    对其他用户关注、解除关注  
    修改个人资料、头像上传  
    查看自己基础信息、帖子和回复、关注和粉丝
    修改密码  

* ## 由个人单独开发，今后会不断地修改完善，属于学习阶段，技术未成熟，请多多包涵

首页图片：
![image](https://github.com/TadZdh/myforum_springboot/blob/master/project_img/home.JPG)
发帖图片：
![image](https://github.com/TadZdh/myforum_springboot/blob/master/project_img/post.JPG)
