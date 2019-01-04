package com.buxz.controller;

import com.buxz.entity.WiselyMessage;
import com.buxz.entity.WiselyResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebSocketController {

    /**
     * 当浏览器向服务端发送请求时，通过@MessageMapping映射/welcome这个地址，类似@RequestMapping
     * 当服务端有消息存在时，会对订阅@SendTo中路径的浏览器发送请求
     * @param message
     * @return
     * @throws Exception
     */
   @MessageMapping("/welcome")
   @SendTo("/topic/getResponse")
    public WiselyResponse say(WiselyMessage message) throws Exception
    {
        // 等待三秒钟
        Thread.sleep(3000);
        return new WiselyResponse("欢迎使用webSocket"+message.getName());
    }
}
