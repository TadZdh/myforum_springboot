package com.example.myforum_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MyforumSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyforumSpringbootApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/toLogin").setViewName("page/login");
                registry.addViewController("/user/settings").setViewName("settings/settings");
                registry.addViewController("/user/changePassword").setViewName("settings/changePassword");
                registry.addViewController("/admin/manage").setViewName("manage/manage");
                registry.addViewController("/admin/categoryEcharts").setViewName("echarts/categoryEcharts");
                registry.addViewController("/admin/newPostEcharts").setViewName("echarts/newPostEcharts");
                registry.addViewController("/admin/userEcharts").setViewName("echarts/userEcharts");
                registry.addViewController("/admin/forbidEcharts").setViewName("echarts/forbidEcharts");
            }
        };
    }
}
