package com.example.myforum_springboot.config;

import com.example.myforum_springboot.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private LoginServiceImpl loginService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(loginService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/bootstrap/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/mycss/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/upload/**").permitAll()
                .antMatchers("/fragments/**").permitAll()
                .antMatchers("/home/**").permitAll()
                .antMatchers("/settings/**").permitAll()
                .antMatchers("/echarts/**").permitAll()
                .antMatchers("/manage/**").permitAll()
                .antMatchers("/page/**").permitAll()
                .antMatchers("/post/**").permitAll()
                .antMatchers("/toLogin").permitAll()
                .antMatchers("/userRegister").permitAll()
                .antMatchers("/toGetCode").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/toLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/post/home")
                .failureUrl("/toLogin?loginError");

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/toLogin?logout");

        http.csrf().disable();

        http.headers().frameOptions().disable();
    }
}
