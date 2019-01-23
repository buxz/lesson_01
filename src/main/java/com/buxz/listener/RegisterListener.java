package com.buxz.listener;

import com.buxz.entity.UserEntityLombok;
import com.buxz.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegisterListener implements ApplicationListener<UserRegisterEvent> {

    /**
     * 实现监听
     * @param registerEvent
     */
    @Override
    public void onApplicationEvent(UserRegisterEvent registerEvent) {
        // 获取注册用户对象
        UserEntityLombok user = registerEvent.getUserEntity();
        // 。。。。
        log.info("实现接口ApplicationListener监听注册信息，"+user.toString());
    }
}
