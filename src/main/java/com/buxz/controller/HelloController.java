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
 * Created by SQ_BXZ on 2018-12-10.
 * @RestController =
 *  @Controller   +
 *  @ResponseBody (该注解用于将Controller 的方法返回对象， 通过适当的HttpMessageConverter转换为指定格式后，写入Response对象的body数据区。)
 *
 *
 */
@RestController
public class HelloController {

    @Autowired
    UserJPA userJPA;

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/helloworld")
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
