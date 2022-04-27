package com.wyw;

import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.RandomStr;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wyw.utils.FinalStaticValue.RESUME_FILE_STORE_PATH_PREFIX;

@SpringBootTest
class WywByksApplicationTests {

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    RandomStr randomStr;

    @Test
    void contextLoads() throws ParseException, IOException {
        System.out.println(randomStr.getRandomString(4));
//        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
//        simpleMailMessage.setSubject("鱼酥你好");
//        simpleMailMessage.setText("我是你的idea");
//        simpleMailMessage.setTo("2268755950@qq.com");
//        simpleMailMessage.setFrom("1260518606@qq.com");
//        javaMailSender.send(simpleMailMessage);

//        String abc="w安慰@qq";
//        String ss="^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
//        System.out.println(abc.matches(ss));


    }

}
