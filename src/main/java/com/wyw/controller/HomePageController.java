package com.wyw.controller;

import com.wyw.service.PartTimeJobService;
import com.wyw.service.PartTimeJobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

@Component
public class HomePageController implements CommandLineRunner {

    @Resource
    HomePageControllerImpl homePageControllerImpl;
    @Resource
    HelloController helloController;
    @Resource
    PartTimeJobService partTimeJobService;


    @Override
    public void run(String... args) throws Exception {

//        Model model = new Model() {
//            @Override
//            public Model addAttribute(String attributeName, Object attributeValue) {
//                return null;
//            }
//
//            @Override
//            public Model addAttribute(Object attributeValue) {
//                return null;
//            }
//
//            @Override
//            public Model addAllAttributes(Collection<?> attributeValues) {
//                return null;
//            }
//
//            @Override
//            public Model addAllAttributes(Map<String, ?> attributes) {
//                return null;
//            }
//
//            @Override
//            public Model mergeAttributes(Map<String, ?> attributes) {
//                return null;
//            }
//
//            @Override
//            public boolean containsAttribute(String attributeName) {
//                return false;
//            }
//
//            @Override
//            public Object getAttribute(String attributeName) {
//                return null;
//            }
//
//            @Override
//            public Map<String, Object> asMap() {
//                return null;
//            }
//        };
//        System.out.println("我进入了首页开始数据");
//        homePageControllerImpl.indexDataInit(model,partTimeJobService);
////        helloController.hello();

    }


}
