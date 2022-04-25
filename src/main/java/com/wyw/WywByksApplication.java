package com.wyw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wyw.dao")
public class WywByksApplication {

    public static void main(String[] args) {
        SpringApplication.run(WywByksApplication.class, args);
    }
}
