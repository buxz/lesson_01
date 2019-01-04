<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WebSocket广播服务</title>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/sockjs.min.js"></script>
    <script src="/js/stomp.min.js"></script>
    <script>
        var stompClient = null;
        // 连接
        function connect() {
            var socket = new SockJS("/endpointWisely");
            stompClient = Stomp.over(socket);
            stompClient.connect({},function (frame) {
                setConnected(true);
                console.log("connected: "+frame);
                stompClient.subscribe("/topic/getResponse",function (response) {
                    showResponse(JSON.parse(response.body).responseMessage);
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
<body>
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
