package com.buxz.controller;

import com.buxz.entity.UserEntity;
import com.buxz.entity.UserEntityLombok;
import com.buxz.jpa.UserJPA;
import com.buxz.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping(value = "/user")
public class UserController {

    private final UserJPA userJPA;

    private final UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserJPA userJPA, UserService userService) {
        this.userJPA = userJPA;
        this.userService = userService;
    }

    @RequestMapping("/list")
    public List<UserEntity> list(){
        logger.info("访问列表------------");
        logger.debug("访问列表------------");
        logger.error("访问列表------------");
        return userService.list();
    }

    /**
     * 分页查询
     * @param page 待查询页数
     * @return
     */
   @RequestMapping("/cutPage")
    public List<UserEntity> cutPage(int page){
       UserEntity userEntity = new UserEntity();
       userEntity.setSize(2);
       userEntity.setPage(page==0?1:page);
       userEntity.setSord("desc");
       // 设置排序对象参数
       Sort sort = new Sort(Sort.Direction.DESC, userEntity.getSidx());
       // 创建分页对象
       PageRequest pageRequest = new PageRequest(userEntity.getPage()-1,userEntity.getSize(),sort);
       // 执行分页查询
       return userJPA.findAll(pageRequest).getContent();
    }

    @RequestMapping("/age")
    public List<UserEntity> age(int age){
        return userJPA.nativeQuery(age);
    }

    @RequestMapping("/deleteWhere")
    public String deleteWhere(){
        userJPA.deleteQuery("李四","021213");
        return "自定义SQL删除数据成功";
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

    @RequestMapping("/register")
    public String register(UserEntityLombok user){
        userService.register(user);
        return "注册成功";
    }

}
