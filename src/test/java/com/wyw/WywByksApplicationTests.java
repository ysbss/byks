package com.wyw;

import com.wyw.pojo.SocketChat;
import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.RandomStr;
import com.wyw.utils.Util;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wyw.utils.FinalStaticValue.RESUME_FILE_STORE_PATH_PREFIX;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WywByksApplicationTests {

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    RandomStr randomStr;

    @Test
    void mapTextLoad(){
//        Map<String,Object> tm=new HashMap<String, Object>();
//        tm.put("a",123);
//        tm.put("a",456);
//        System.out.println(tm.get("a"));
//        System.out.println(tm.size());
        System.out.println(System.currentTimeMillis());
        int a=0;
        while(a<1000000){
            a++;
        }
        System.out.println(System.currentTimeMillis());

    }


    @Test
    void contextLoad() throws ParseException, IllegalAccessException, InstantiationException {
//        Util util=new Util();
//        Map<String ,Object> sm=new HashMap<String,Object>();
//        SocketChat sc=new SocketChat();
//        sc.setScId("413214");
//        sm.put("scId","8547895231");
//        sm.put("id","23412");
//        Map<String, Object> stringObjectMap = util.pojoToMap(sc);
//        SocketChat socketChat = util.mapToPojo(sm, SocketChat.class);
//        System.out.println(socketChat);
//        System.out.println(stringObjectMap);
        SimpleDateFormat sdfq=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sd=new SimpleDateFormat("yyyy MM dd HH:mm:ss zzz EEE");

        System.out.println(sd.format(sdfq.parse(ZonedDateTime.parse("2022-07-27T03:58:34.341Z").plusHours(8).toString())));
//        SimpleDateFormat sdf=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US);
//        SimpleDateFormat sd=new SimpleDateFormat("yyyy/MM/dd");
//        SimpleDateFormat sd=new SimpleDateFormat("yyyy MM dd HH:mm:ss zzz EEE");
//        System.out.println(sd.format(sdf.parse("Fri Apr 22 20:03:52 CST 2022")));
//        System.out.println(sd.format(sdf.parse("Sun Apr 06 00:22:47 CST 2022")));
//        System.out.println(sd.format(sdf.parse("Sun Apr 05 00:22:47 CST 2022")));
//        System.out.println(sd.format(sdf.parse("Sun Apr 04 00:22:47 CST 2022")));
//        System.out.println(sd.format(sdf.parse("Sun Apr 03 00:22:47 CST 2022")));
//        System.out.println(sd.format(sdf.parse("Sun Apr 03 00:22:47 CST 2022")));
//        System.out.println(sd.format(sdf.parse("Sun Apr 02 00:22:47 CST 2022")));
//        System.out.println(sd.format(sdf.parse("Sun Apr 02 00:22:47 CST 2022")));



    }


    @Test
    void contextLoads() throws ParseException, IOException {

//        String strGMT="Thu, 02 Jul 2015 05:49:30 GMT";
          String utcTime="2022-07-24T08:04:22.581Z";
        System.out.println(ZonedDateTime.parse(utcTime));
        System.out.println(ZonedDateTime.parse(utcTime).toLocalDateTime());
        System.out.println(ZonedDateTime.parse(utcTime).toLocalDateTime().plusHours(8));
        System.out.println(ZonedDateTime.parse(utcTime).plusHours(8));
        ;
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
//        System.out.println(dtf.format(ZonedDateTime.parse(utcTime).plusHours(8)));
        //上面可以，下面不行
//        System.out.println(dtf.format(ZonedDateTime.parse(utcTime).toLocalDateTime().plusHours(8)));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        System.out.println(sdf.parse(ZonedDateTime.parse(utcTime).plusHours(8).toString()));
        System.out.println(sdf.parse(ZonedDateTime.parse(utcTime).plusHours(8).toString()).toString());
        System.out.println(sdf.format(sdf.parse(ZonedDateTime.parse(utcTime).plusHours(8).toString())));

//        SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z",Locale.US);
//        SimpleDateFormat sd = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
//        df.setTimeZone(TimeZone.getTimeZone("GMT"));
//        System.out.println(sdf.format(sdf.parse(strGMT)));
//        System.out.println(sd.format(sdf.parse(strGMT)));
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//        SimpleDateFormat re=new SimpleDateFormat("yyyy/MM/dd");
//        System.out.println(sdf.format(re.parse("2022/04/22")));

        String preUuid = UUID.randomUUID().toString();
//        System.out.println(preUuid);

        //第一种方法生成UUID，去掉“-”符号
//        System.out.println(UUID.randomUUID().toString().replace("-", ""));



    }


}
