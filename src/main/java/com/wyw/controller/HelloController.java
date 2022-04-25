package com.wyw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {


    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
//        System.out.println("我从项目一开始被启动了");
        return "hello";
    }

}

