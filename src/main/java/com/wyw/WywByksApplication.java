package com.wyw;

import com.wyw.controller.WebSocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication

@MapperScan("com.wyw.dao")

public class WywByksApplication {

    public static void main(String[] args) {
        SpringApplication.run(WywByksApplication.class, args);
//        SpringApplicationBuilder builder = new SpringApplicationBuilder(WywByksApplication.class);
//        builder.headless(false).run(args);
        //为了让boot自动发送，设置了vmOption可以不用这个
//以下为解决websocket不能注入的第四种方法 初始化时获得上下文
//        SpringApplication springApplication = new SpringApplication(WywByksApplication.class);
//        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(WywByksApplication.class, args);
//
//        //解决WebSocket不能注入的问题
//        WebSocket.setApplicationContext(configurableApplicationContext);


    }
}
