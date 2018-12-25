package com.buxz.config;

import com.buxz.jpa.UserJPA;
import com.buxz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by SQ_BXZ on 2018-12-17.
 *  @EnableWebSecurity 注解开启Spring Security的功能
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * 完成自定义认证实体注入
     * @return
     */
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserService();
    }

    /**
     * 在内存中创建了一个用户，该用户的名称为bxz，密码为 021213 ，用户角色为USER。
     * @param auth
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("bxz")
//                .password("021213")
//                .roles("USER");
//    }

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests() // authorizeRequests()  定义哪些URL需要被保护、哪些不需要被保护
                    .antMatchers("/", "/home").permitAll() // "/" 和 "home" 不需要任何认证就可以访问
                    .anyRequest().authenticated() // 所有请求必须登录之后访问
                .and().formLogin() // formLogin() 定义当需要用户登录时候，转到的登录页面
                    .loginPage("/login") // http://localhost:8080/login 直接访问登录页面，和WebConfiguration.addViewControllers 添加的路径匹配
                    .failureUrl("/login/index") //登录失败跳转链接，需要GET方法
                    .successForwardUrl("/login/success") // 登录成功之后跳转链接，需要post方法
                    .permitAll() // 登录页面，可以直接访问
                .and().logout() // http://localhost:8080/logout 直接退出
                    .permitAll(); // 注销请求可直接访问
    }
}
