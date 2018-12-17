package com.buxz.config;

import com.buxz.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by SQ_BXZ on 2018-12-17.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * 完成自定义认证实体注入
     *
     * @return
     */
    @Bean
    UserDetailsService userSecurityService(){
        return new UserService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // springSecurity4.0后，默认开启了CSRD拦截
                .authorizeRequests()
                .anyRequest().authenticated()// 所有请求必须登录之后访问
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .permitAll() // 登录页面，可以直接访问
                .and()
                    .logout()
                    .permitAll(); // 注销请求可直接访问
    }
}
