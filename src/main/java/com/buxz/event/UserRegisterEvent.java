package com.buxz.event;

import com.buxz.entity.UserEntityLombok;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


/**
 * 用户注册事件
 * 监听都是围绕着事件来挂起的
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {

    // 注册用户对象
    private UserEntityLombok userEntity;

    /**
     * 重写构造函数
     * @param source 发生事件的对象
     * @param userEntity 注册用户对象， 该对象可以在监听内被获取
     */
    public UserRegisterEvent(Object source, UserEntityLombok userEntity) {
        super(source);
        this.userEntity = userEntity;
    }
}
