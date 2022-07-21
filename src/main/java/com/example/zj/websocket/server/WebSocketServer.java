package com.example.zj.websocket.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{username}")
@Component
@Slf4j
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static Map<String, WebSocketServer> clients = new ConcurrentHashMap<String, WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String username="";



/**
 * 连接建立成功调用的方法
 */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        clients.put(username,this);     //加入map中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:"+username+",当前在线人数为" + getOnlineCount());
        this.username=username;

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

/**
 * 连接关闭调用的方法
 */
    @OnClose
    public void onClose() {
        clients.remove(this);  //从map中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

/**
 * 收到客户端消息后调用的方法
 *messageType 1代表让android开始发送数据，2代表让android停止发送数据，3代表接收方未连接，4代表音频数据
 * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("收到来自窗口"+username+"的信息:"+message);
        JSONObject jsonObject = JSON.parseObject(message);
        int messageType=jsonObject.getIntValue("messageType");
        String textMessage = jsonObject.getString("textMessage");
        String fromUsername = jsonObject.getString("fromUsername");
        String toUsername = jsonObject.getString("toUsername");
        if(clients.containsKey(toUsername)){
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("messageType",4);
            map1.put("textMessage",textMessage);
            map1.put("fromUsername",fromUsername);
            map1.put("toUsername",toUsername);
            sendMessageTo(JSON.toJSONString(map1),toUsername);
        }
        else
        {
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("messageType",3);
            map1.put("textMessage","接收方未连接,请稍后重试");
            map1.put("fromUsername",fromUsername);
            map1.put("toUsername",toUsername);
            sendMessageTo(JSON.toJSONString(map1),fromUsername);
        }
    }

/**
 *
 * @param session
 * @param error
 */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

/**
 * 实现服务器主动推送
 */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

/**
 * 给特定对象发送消息
 * */
public void sendMessageTo(String message, String toUserName) throws IOException {
    for (WebSocketServer item : clients.values()) {
        if (item.username.equals(toUserName) ) {
            item.session.getAsyncRemote().sendText(message);
            break;
        }
    }
}

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
    public static Map<String, WebSocketServer> getWebSocketSet() {
        return clients;
    }

}

