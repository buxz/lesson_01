package com.buxz.controller;

import com.buxz.entity.UserEntity;
import com.buxz.jpa.UserJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 这个控制器我们开放，让SpringSecurity不去管理
 *
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @Autowired
    UserJPA userJPA;

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/say")
    public String HelloWorld(){
        return "Hello World!";
    }


    @RequestMapping("/list")
    public List<UserEntity> list(){
        logger.info("访问列表------------");
        logger.debug("访问列表------------");
        logger.error("访问列表------------");
        return userJPA.findAll();
    }

    @RequestMapping("/add")
    public UserEntity add(){
        UserEntity entity = new UserEntity();
        entity.setUsername("李四");
        entity.setAddress("梅楼");
        entity.setAge(12);
        entity.setPassword("021213");
        return userJPA.save(entity);
    }

    @RequestMapping("/delete")
    public List<UserEntity> delete(Long id){
        userJPA.delete(id);
        return userJPA.findAll();
    }
}
