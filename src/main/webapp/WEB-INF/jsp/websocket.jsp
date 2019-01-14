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
