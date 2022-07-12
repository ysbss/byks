package com.wyw;

import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.RandomStr;
import lombok.SneakyThrows;
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
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
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





    }


}
