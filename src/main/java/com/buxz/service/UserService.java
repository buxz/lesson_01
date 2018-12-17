package com.buxz.service;

import com.buxz.entity.UserEntity;
import com.buxz.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SQ_BXZ on 2018-12-17.
 * @CacheConfig 该类参加缓存，如果方法内的@Cacheable没有添加key值，那么会自动使用cacheNames 配置参数并追加方法名
 * @Cacheable 配置方法的缓存参数，可自定义缓存的key以及value。
 *
 */
@Service
@CacheConfig(cacheNames= "user-bxz")
public class UserService implements UserDetailsService {

    @Autowired
    private UserJPA userJPA;

    @Cacheable
    public List<UserEntity> list(){
        return userJPA.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userJPA.findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("未查询到用户："+username);
        }
        return user;
    }

}
