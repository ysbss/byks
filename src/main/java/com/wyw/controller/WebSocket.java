package com.wyw.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyw.config.HttpSessionConfigurator;
import com.wyw.config.MyEndpointConfig;
import com.wyw.pojo.SocketChat;
import com.wyw.service.SocketChatService;
import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 鱼酥不是叔
 */
@Data
@Component
@ServerEndpoint(value = "/webSocket/{receiveId}",configurator = HttpSessionConfigurator.class)
public class WebSocket<L>
//        implements ApplicationContextAware  这是第二种方法要实现的接口
{
    private static CopyOnWriteArraySet<WebSocket> webSockets=new CopyOnWriteArraySet<WebSocket>();
    private static Map<Long,Map<Long, List<Session>>> sessionMap=new HashMap<Long, Map<Long, List<Session>>>();
    private   List<Map<String,Object>> unStorageSocketChatList=new ArrayList<Map<String,Object>>();
    private Session session;
    private Long sendId;
    private Long receiveId;
//    private WebSocket selfWebSocket;

    private final Object lock=new Object();
    private static volatile long startTime=0L;
    private static volatile long saveTime=0L;

//这种方法是第一种在wsc中注入，必须要public static修饰
//    public static SocketChatService socketChatService;

//    private static ApplicationContext applicationContext;
//    private SocketChatService socketChatService;
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        WebSocket.applicationContext=applicationContext;
//    }
//以上是第二种方法 要实现ApplicationContextAware这个接口

    private static SocketChatService socketChatService;
    @Resource
    public void setSocketChatService(SocketChatService socketChatService){
        WebSocket.socketChatService=socketChatService;
    }
//第三种方法直接set对应service

//    private static ApplicationContext applicationContext;
//    public static void setApplicationContext(ApplicationContext applicationContext) {
//        WebSocket.applicationContext = applicationContext;
//    }
////第四种方法初始化时获取上下文

    private static Util util;
    @Resource
    public void setUtil(Util util){
        WebSocket.util=util;
    }

    /**用来记录sessionId和该session进行绑定*/
    @Deprecated
    private static Map<String,Session> map = new HashMap<String, Session>();


    private  int sendChatCount =0;
    //用来表示一次发送了多少条消息



    @OnOpen
    public void onOpen(Session session, EndpointConfig config,@PathParam(value = "receiveId") Long receiveId) throws IOException {
        this.session=session;
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.sendId =Long.valueOf(httpSession.getAttribute("currentChatId").toString());
        this.receiveId=receiveId;

//        this.selfWebSocket=this;

//        if (httpSession.getAttribute("currentComId")!=null){
//            map.put(httpSession.getAttribute("currentComId").toString(),session);
//            System.out.println("dasdasdqwdqw:"+map.get(httpSession.getAttribute("currentComId").toString()));
//            testId =httpSession.getAttribute("currentComId").toString();
//
////            sendId=Long.valueOf(httpSession.getAttribute("currentComId").toString());
//        }
//        else {
//            map.put(httpSession.getAttribute("currentStuId").toString(),session);
//            System.out.println("dqwdqwdqwdqw:"+map.get(httpSession.getAttribute("currentStuId").toString()));
//            testId =httpSession.getAttribute("currentStuId").toString();
////            sendId=Long.valueOf(httpSession.getAttribute("currentStuId").toString());
//        }




//        synchronized (lock){
//            if (!map.containsKey(httpSession.getAttribute("currentChatId").toString())){
//                for (String s :
//                        map.keySet()) {
//                    System.out.println("mapKey:"+s);
//                }
//                map.put(httpSession.getAttribute("currentChatId").toString(),session);
//                System.out.println("我进来了没有这个map没有这个key的情况");
//                System.out.println("连接时map取得的session:"+map.get(httpSession.getAttribute("currentChatId").toString()));
//                System.out.println("此次session:"+this.session);
//                webSockets.add(this);
//                System.out.println("有新连接加入！当前在线人数为" + webSockets.size());
//                System.out.println("mapSize:"+map.size());
//                this.session.getBasicRemote().sendText("恭喜您成功连接上WebSocket-->当前在线人数为："+webSockets.size()+",此时发送人id为:"+ sendId +"ta是："+httpSession.getAttribute("currentName").toString());
//            }else {
//                for (String s :
//                        map.keySet()) {
//                    System.out.println("mapKey:"+s);
//                }
//                System.out.println("我进来了有这个session的情况");
//                onClose();
//                for (String s :
//                        map.keySet()) {
//                    System.out.println("mapKey:"+s);
//                }
//                this.session.getBasicRemote().
//                        sendText("恭喜您成功连接上WebSocket-->当前在线人数为："+
//                                webSockets.size()+",此时发送人id为:"+ sendId +"ta是："+
//                                httpSession.getAttribute("currentName").toString());
//
//
//                //                this.session.close();
//
//            }
//
//        }
//        this.session.getAsyncRemote().sendText("恭喜您成功连接上WebSocket-->当前在线人数为："+webSockets.size()+",此时发送人id为:"+ sendId +"ta是："+httpSession.getAttribute("currentName").toString());
//                Map<Long,Session> smn=new HashMap<Long,Session>();
//                smn.put(receiveId,session);
        if (sessionMap.
                get(Long.valueOf(httpSession.getAttribute("currentChatId")
                        .toString()))
                ==null
//                &&sessionMap.get(Long.valueOf(httpSession.getAttribute("currentChatId").toString())).get(receiveId)==null
        ){
            //说明此时是新建立的session链接
            sessionMap.put(Long.valueOf(httpSession.getAttribute("currentChatId").toString()),new HashMap<Long, List<Session>>(){{put(receiveId,new ArrayList<Session>(){{add(session);}});}});

//            sessionMap.put(Long.valueOf(httpSession.getAttribute("currentChatId").toString()),new HashMap<Long, List<Session>>(){{put(receiveId,Stream.of(session).collect(Collectors.toList()));}});
//            sessionMap.put(Long.valueOf(httpSession.getAttribute("currentChatId").toString()),new HashMap<Long, List<Session>>(){{put(receiveId,Stream.of(session).collect(Collectors.toList()));}});
            System.out.println("连接时map取得的session:"+sessionMap.get(Long.valueOf(httpSession.getAttribute("currentChatId").toString())).get(receiveId)+"此时接受id："+sessionMap.get(Long.valueOf(httpSession.getAttribute("currentChatId").toString())).keySet() );
            System.out.println("此次session:"+this.session);
            webSockets.add(this);
            System.out.println("有新连接加入！当前在线人数为" + webSockets.size());
            System.out.println("mapSize:"+sessionMap.size());
//            this.session.getBasicRemote().sendText("恭喜您成功连接上WebSocket-->当前在线人数为："+webSockets.size()+",此时发送人id为:"+ sendId +"ta是："+httpSession.getAttribute("currentName").toString());
        }else if(!sessionMap.get(Long.valueOf(httpSession.getAttribute("currentChatId").toString())).isEmpty()&&
                sessionMap.get(Long.valueOf(httpSession.getAttribute("currentChatId").toString())).containsKey(this.receiveId)){
            //这是同一个send人，打开了同一个receive人
            sessionMap.get(Long.valueOf(httpSession.getAttribute("currentChatId").toString())).get(this.receiveId).add(this.session);

            System.out.println("同一个sendId，放入了相同的receiveId");
//            this.session.getBasicRemote().sendText("恭喜您成功连接上WebSocket-->当前在线人数为："+webSockets.size()+",此时发送人id为:"+ sendId +"ta是："+httpSession.getAttribute("currentName").toString());
        }
        else{
            //这个是同一个send人，打开了另一个不同的receive人
            sessionMap.get(Long.valueOf(httpSession.getAttribute("currentChatId").toString())).put(this.receiveId,new ArrayList<Session>(){{add(session);}});
            webSockets.add(this);
            System.out.println("同一个sendId，放入了不同的receiveId");
//            this.session.getBasicRemote().sendText("恭喜您成功连接上WebSocket-->当前在线人数为："+webSockets.size()+",此时发送人id为:"+ sendId +"ta是："+httpSession.getAttribute("currentName").toString());
        }
        startTime = System.currentTimeMillis();
        System.out.println(startTime);
    }

    @OnClose
    public void onClose(){
        if (webSockets.contains(this)) {
            if (sessionMap.get(this.sendId).get(this.receiveId).indexOf(this.session)==0
                    &&!sessionMap.get(this.sendId).get(this.receiveId).isEmpty()
                    &&sessionMap.get(this.sendId).get(this.receiveId).size()!=1
            ){
                //从添加可知，每个list的第一个元素才是真正加入了websocket中的,而且这个时候开了一堆同一个receive的聊天窗口
                for (WebSocket ws:webSockets
                     ) {
                    if (ws.session.equals(this.session)){
                        //说明此时打开的session就在webSocket里
                        WebSocket newWebSocket = new WebSocket();
                        newWebSocket.setSession(sessionMap.get(this.sendId).get(this.receiveId).get((sessionMap.get(this.sendId).get(this.receiveId).indexOf(this.session))+1));

                        webSockets.add(newWebSocket);

                        webSockets.remove(ws);
                    }
                }
                System.out.println("真正的在webSocket里的");

            }else{
                webSockets.remove(this);
                System.out.println("测试234124134124312");
            }
        }else{
            if (sessionMap.get(this.sendId).get(this.receiveId).indexOf(this.session)==0
                    &&!sessionMap.get(this.sendId).get(this.receiveId).isEmpty()
                    &&sessionMap.get(this.sendId).get(this.receiveId).size()!=1
            ){
                WebSocket newWebSocket = new WebSocket();
                newWebSocket.setSession(sessionMap.get(this.sendId).get(this.receiveId).get((sessionMap.get(this.sendId).get(this.receiveId).indexOf(this.session))+1));
                webSockets.add(newWebSocket);
                webSockets.removeIf(ws -> ws.session.equals(this.session));
                System.out.println("不存在这个key，但是成为了list的第一个");
            }
            else{
                webSockets.removeIf(ws -> ws.session.equals(this.session));
                System.out.println("不存在这个ket，也是不list的第一个");
            }

        }
        System.out.println("关闭时获得了sm里的map的k值对："+sessionMap.get(sendId).keySet());

            //如果只有一个sendId的才remove掉不然就不remove

            if (sessionMap.get(sendId).get(receiveId).size()==1){
                //要关闭svId的最后一个session
                System.out.println("要关闭svId的最后一个session");
                sessionMap.get(sendId).get(receiveId).remove(sessionMap.get(sendId).get(receiveId).size()-1);
                sessionMap.get(sendId).get(receiveId).clear();
                sessionMap.get(sendId).remove(receiveId);
            }else {
                //关闭非最后一个svId，这里用this.session是因为无法确定具体下标
                System.out.println("关闭非最后一个svId");
                sessionMap.get(sendId).get(receiveId).remove(this.session);
            }
//            if (!unStorageSocketChatList.isEmpty()){
//                socketChatService.saveGroupSocketChat(unStorageSocketChatList);
//                //用一、三种方法
////                applicationContext.getBean(SocketChatService.class).saveGroupSocketChat(unStorageSocketChatList);
//                //上面是用第二、四种方法
//                unStorageSocketChatList.clear();
//            }
//        调用ajax就不能在这里保存方法了
        System.out.println("有一连接关闭！当前在线人数为" + webSockets.size());
    }

    @OnMessage
    public void onMessage(String socketMsg) throws IOException, ParseException, IllegalAccessException {
        System.out.println("来自客户端的消息:" + socketMsg);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sd=new SimpleDateFormat("yyyy MM dd HH:mm:ss zzz EEE");
        ObjectMapper objectMapper = new ObjectMapper();
        SocketChat socketChat=objectMapper.readValue(socketMsg,SocketChat.class);
        socketChat.setScMsgSendTime(sd.format(sdf.parse(ZonedDateTime.parse(socketChat.getScMsgSendTime()).plusHours(8).toString())));
        //将前端传来的utc格式转化为cst格式

        System.out.println(socketChat);

            unStorageSocketChatList.add(util
                    .pojoToMap(socketChat));


        sendChatCount++;

        saveTime= System.currentTimeMillis();
//        Session scSend=map.get(socketChat.getScSendId().toString());
//        Session scSend=sessionMap.get(socketChat.getScSendId()).get(socketChat.getScReceiveId()).get();
        Session scSend=null;
        for (Session s:
        sessionMap.get(socketChat.getScSendId()).get(socketChat.getScReceiveId())) {
            if (s.equals(this.session)){
                scSend=s;
            }
        }

//        System.out.println("发送消息时获取的send："+scSend);
//        System.out.println(":"+socketChat.getScSendId().toString());
//        System.out.println("scSend:"+scSend);
//        System.out.println(map.get("1002"));
        Session scReceive=null;
//        if (sessionMap.get(socketChat.getScReceiveId())!=null){
//            scReceive=sessionMap.get(socketChat.getScReceiveId())
//                    .get(socketChat.getScSendId());
//        }

//        System.out.println(sendId+":"+socketChat.getScReceiveId().toString());
//        System.out.println("scReceive"+scReceive);
        //将这段消息存入数据库

//                scSend.getAsyncRemote().sendText(socketChat.getScChatMsg());




//                scReceive.getAsyncRemote().sendText(socketChat.getScChatMsg());
            if (sessionMap.get(socketChat.getScReceiveId())!=null){
                for (Session s:
                        sessionMap.get(socketChat.getScSendId()).get(socketChat.getScReceiveId())) {
                    scSend=s;
                    synchronized (scSend) {
                        scSend.getBasicRemote().sendText(socketChat.getScChatMsg());
                    }
                    //对于list中存储的每一个session都发送消息，如果此时页面开着的话就会都接受到
                }
                for (Session s:sessionMap.get(socketChat.getScReceiveId()).get(socketChat.getScSendId())
                ) {

                    scReceive=s;
                    synchronized (scReceive){
                    scReceive.getBasicRemote().sendText(socketChat.getScChatMsg());
                    }
                }
            }else {
                System.out.println("send是否开启"+scSend.isOpen());
                synchronized (scSend){
//                    scSend.getAsyncRemote().sendText("对方不在线");
                    scSend.getBasicRemote().sendText(socketChat.getScChatMsg());
                    System.out.println("对方不在线");
                }


            }
        if (unStorageSocketChatList.size()>= FinalStaticValue.SAVE_COUNT||sendChatCount>=FinalStaticValue.SAVE_COUNT||(saveTime-startTime)>FinalStaticValue.SAVE_BETWEEN_TIME){
                System.out.println("来保存咯");
            for (Map m:
                 unStorageSocketChatList) {
                System.out.println("保存时list里面所有的uuid："+m.get("scId"));
            }
                socketChatService.saveGroupSocketChat(unStorageSocketChatList);
                sendChatCount=0;
                startTime= System.currentTimeMillis();
                unStorageSocketChatList.clear();
            }


            //加锁是因为发送速度过快异步会报错故用这种同步方式

//        if(scReceive != null&& scReceive.isOpen()){
//
//
//        }else {
//            System.out.println("send是否开启"+scSend.isOpen());
//            scSend.getAsyncRemote().sendText("对方不在线");
//            System.out.println("对方不在线");
//        }

//        sendMessage(socketMsg); 群发消息
    }

    @OnError
    public void onError(Session session,Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
    public void sendMessage(String message){
        for (WebSocket webSocket:webSockets
             ) {
//            webSocket.session.getBasicRemote().sendText(message);
            webSocket.session.getAsyncRemote().sendText(message);
        }
    }




//    @Override
//    public boolean equals(Object o) {
//        if (this == o){ return true;}
//        if (o == null || getClass() != o.getClass()){ return false;}
//        WebSocket webSocket = (WebSocket) o;
//        return Objects.equals(session, webSocket.session) && Objects.equals(sendId, webSocket.sendId) && Objects.equals(receiveId, webSocket.receiveId) && Objects.equals(lock, webSocket.lock) && Objects.equals(socketChatService, webSocket.socketChatService);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(session, sendId, receiveId, lock, socketChatService);
//    }
}
