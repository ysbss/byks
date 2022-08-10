package com.wyw.config;

import com.wyw.controller.WebSocket;
import com.wyw.service.SocketChatService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

//    /**
//     * 支持注入其他接口类
//     */
//    @Bean
//    public MyEndpointConfig  newMyEndpointConfigure (){
//        return new MyEndpointConfig ();
//    }


//    @Resource
////    @Autowired
//    public void setSocketChatService(SocketChatService socketChatService){
//        WebSocket.socketChatService=socketChatService;
//    }
}
