<%--
  Created by IntelliJ IDEA.
  User: zj
  Date: 2020/9/21
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实时传输一对一测试</title>
</head>
<script>
    var socket;
    if(typeof(WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    }else{
        console.log("您的浏览器支持WebSocket");
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        //等同于
        index = new WebSocket("ws://localhost:8080/websocket/2");
        //socket = new WebSocket("${basePath}websocket/${cid}".replace("http","ws"));
        //打开事件
        index.onopen = function() {
            console.log("Socket 已打开");
            //socket.send("这是来自客户端的消息" + location.href + new Date());
        };
        //获得消息事件
        index.onmessage = function(msg) {
            console.log(msg.data);
            //发现消息进入    开始处理前端触发逻辑
        };
        //关闭事件
        index.onclose = function() {
            console.log("Socket已关闭");
        };
        //发生了错误事件
        index.onerror = function() {
            alert("Socket发生了错误");
            //此时可以尝试刷新页面
        }
        //离开页面时，关闭socket
        //jquery1.8中已经被废弃，3.0中已经移除
        // $(window).unload(function(){
        //     socket.close();
        //});
    }
</script>

<body>
hello world!
</body>
</html>
