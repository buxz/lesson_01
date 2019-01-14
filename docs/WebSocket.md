## 配置广播服务（webSocket）
### 1. 添加依赖
```html
	    <!-- 配置广播服务 websocket -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-websocket</artifactId>
		</dependency>
		
```
### 2. 配置 WebSocketConfiguration
```java

package com.buxz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @EnableWebSocketMessageBroker注解 开启使用STOMP协议来传输消息
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer{


    /**
     * 添加了对应的STOMP使用SockJS协议
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // SocketJS 协议 对应的请求地址，客户端建立链接时需要请求该地址
        registry.addEndpoint("/endpointWisely").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 客户端需要订阅该地址，
        registry.enableSimpleBroker("/topic");
    }
}

```
### 3. 定义实体类
> 浏览器向服务端发送消息的对象
```java

package com.buxz.entity;

/**
 * Created by SQ_BXZ on 2019-01-04.
 * 浏览器向服务端发送消息参数
 */
public class WiselyMessage {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

```
> 服务端向浏览器发送消息的对象
```java

package com.buxz.entity;

/**
 * Created by SQ_BXZ on 2019-01-04.
 * 服务端向浏览器广播消息参数
 */
public class WiselyResponse {
    private String message;

    public WiselyResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

```
### 4. 配置controller
```java
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

```

### 5. 配置客户端页面
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WebSocket广播服务</title>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/sockjs.min.js"></script>
    <script src="/js/stomp.min.js"></script>
    <script>
        var stompClient = null;
        /**
         * 连接
         *  通过SockJs来注册了endpoine
         *  并且通过客户端开启通过连接
         *  通过注册/topic/getReponse路径来回去服务端向浏览器端的请求数据内容
         */
        function connect() {
            var socket = new SockJS("/endpointWisely");
            stompClient = Stomp.over(socket);
            stompClient.connect({},function (frame) {
                setConnected(true);
                console.log("connected: "+frame);
                stompClient.subscribe("/topic/getResponse",function (response) {
                    showResponse(JSON.parse(response.body).message);
                })
            })

        }
        // 设置连接状态控制显示隐藏
        function setConnected(connected) {
            $("#connect").attr("disabled",connected);
            $("#disconnect").attr("disabled",!connected);
            if(connected){
                $("#inputDiv").show();
            }else {
                $("#inputDiv").hide();
            }
            $("#response").html("");
        }
        // 显示socket 返回消息内容
        function showResponse(message) {
            console.log("返回信息=="+message);
            $("#response").html(message);
        }

        // 断开连接
        function disconnect() {
            if (stompClient != null){
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("disconnected!");
        }

        //发送名称到后台
        function sendName(){
            var name = $("#name").val();
            stompClient.send("/welcome",{},JSON.stringify({'name':name}));
        }
    </script>
</head>
<body onload="disconnect()">
<button id="connect" onclick="connect()">连接</button>
<button id="disconnect" onclick="disconnect()">断开连接</button>
<div id="inputDiv">
    输入名称 : <input type="text" id="name"><br>
    <button id="sendName" onclick="sendName()"> 发送</button><br>
    <p id="response">

    </p>
</div>
</body>
</html>

```

### 6. 验证
1. 访问客户端页面
2. 点击连接按钮，与服务端建立连接，订阅 请求路径，当该路径有返回的时候，显示在页面上
3. 输入名称，点击发送
4. 打开多个页面，会看到各个页面（客户端）自动同步信息