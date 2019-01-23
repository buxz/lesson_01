package com.buxz.listener;

import com.buxz.entity.UserEntityLombok;
import com.buxz.event.UserRegisterEvent;
import com.buxz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegisterListener_Smart implements SmartApplicationListener{

    /**
     * 该方法返回true&supportsEventType同样返回true时，才会调用该监听内的onApplicationEvent方法
     * @param aClass 接收到的监听事件类型
     * @return
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        // 只有UserRegisterEvent监听类型才会执行下面逻辑
        return aClass == UserRegisterEvent.class;
    }

    /**
     * 该方法返回true&supportsEventType同样返回true时，才会调用该监听内的onApplicationEvent方法
     * @param aClass
     * @return
     */
    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        // 只有在UserService内发布的UserRegisterEvent事件时才会执行下面逻辑
        return aClass == UserService.class;
    }

    /**
     * supportsEventType & supportsSourceType 两个方法返回true时调用该方法执行业务逻辑
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        //转换事件类型
        UserRegisterEvent userRegisterEvent = (UserRegisterEvent) applicationEvent;
        //获取注册用户对象信息
        UserEntityLombok user = userRegisterEvent.getUserEntity();
        //.../完成注册业务逻辑
        log.info("实现接口SmartApplicationListener注册信息，"+user.toString());

    }

    /**
     *  同步情况下监听执行的顺序
     *  这个方法就可以解决执行监听的顺序问题，return的数值越小证明优先级越高，执行顺序越靠前。
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
