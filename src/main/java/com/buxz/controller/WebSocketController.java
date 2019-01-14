package com.buxz.controller;

import com.buxz.entity.WiselyMessage;
import com.buxz.entity.WiselyResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    /**
     * 当浏览器向服务端发送请求时，通过@MessageMapping映射/welcome这个地址，类似@RequestMapping
     * 当服务端有消息存在时，会对订阅@SendTo中路径的浏览器发送请求
     * @param message
     * @return
     * @throws Exception
     */
   @MessageMapping("/welcome")
   @SendTo("/topic/getResponse") // "/topic" 该地址已经配置在WebSocketConfiguration
    public WiselyResponse say(WiselyMessage message) throws Exception
    {
        // 等待三秒钟
        Thread.sleep(1000);
        return new WiselyResponse("欢迎使用webSocket， "+message.getName());
    }
}
