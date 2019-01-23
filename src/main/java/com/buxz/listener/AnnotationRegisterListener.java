package com.buxz.listener;

import com.buxz.entity.UserEntityLombok;
import com.buxz.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AnnotationRegisterListener {

    @EventListener
    public void register(UserRegisterEvent registerEvent){
        // 获取注册用户对象
        UserEntityLombok user = registerEvent.getUserEntity();

        // ....

        // 输出注册信息
        log.info("@EventListener注册信息，"+user.toString());
    }
}
