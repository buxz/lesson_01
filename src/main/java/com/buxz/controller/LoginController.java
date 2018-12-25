package com.buxz.controller;

import com.buxz.entity.UserEntity;
import com.buxz.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private UserJPA userJPA;

    /**
     * 初始化登录页面
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String login_view(String errorMessage){
        System.out.println("错误信息---"+errorMessage);
        return "login";
    }

    /**
     * 登录成功页面
     * @return
     */
    @RequestMapping(value = "/success",method = RequestMethod.POST)
    public String index(){
        return "main";
    }


    @ResponseBody
    @RequestMapping(value = "/form")
    public String login(UserEntity user, HttpServletRequest request)
    {
        //登录成功
        boolean flag = true;
        String result = "登录成功";
        //根据用户名查询用户是否存在
        UserEntity userEntity = userJPA.findOne(
                (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.where(criteriaBuilder.equal(root.get("name"), user.getUsername()));
            return null;
        });
        //用户不存在
        if(userEntity == null){
            flag = false;
            result = "用户不存在，登录失败";}
        //密码错误
        else if(!userEntity.getPassword().equals(user.getPassword())){
            flag = false;
            result = "用户密码不相符，登录失败";
        }
        //登录成功
        if(flag){
            //将用户写入session
            request.getSession().setAttribute("_session_user",userEntity);
        }
        return result;
    }
}
