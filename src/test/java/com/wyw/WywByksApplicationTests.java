package com.wyw;

import com.wyw.pojo.SocketChat;
import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.RandomStr;
import com.wyw.utils.SendSms;
import com.wyw.utils.Util;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.tika.Tika;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wyw.utils.FinalStaticValue.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WywByksApplicationTests {

    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Resource
    RandomStr randomStr;

    @Resource
    DataSource dataSource;

    @Test
    void sendMessageByZhenZi(){
        SendSms.send("13767484001","1234");
    }

    @Test
    void fileDetect() throws IOException {

//        File f=new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+"上海哔哩哔哩"+File.separator+File.separator+"HII.jpg");
//        InputStream is =new FileInputStream(f);
//        BufferedInputStream bis = new BufferedInputStream(is);
//        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
//        AutoDetectParser parser = new AutoDetectParser();
//        Detector detector = parser.getDetector();
//        Detector detector = new AutoDetectParser().getDetector();
//        Metadata md = new Metadata();
//        md.add(Metadata.RESOURCE_NAME_KEY, f.getName());
//        MediaType mediaType = detector.detect(bis, md);
//        MediaType mediaType = new AutoDetectParser().getDetector().detect(bis, md);
//        MediaType mediaType = new AutoDetectParser().getDetector().detect(new BufferedInputStream(new FileInputStream(f)), md);
//        MediaType mediaType = new AutoDetectParser().getDetector().detect(new BufferedInputStream(new FileInputStream(f)), new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, f.getName());}});
//        System.out.println(mediaType.toString());
//        System.out.println(new AutoDetectParser().getDetector().detect(new BufferedInputStream(new FileInputStream(f)), new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, f.getName());}}).toString());
        System.out.println(new AutoDetectParser().getDetector().detect(
                new BufferedInputStream(new FileInputStream(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+"上海哔哩哔哩"+File.separator+File.separator+"HII.jpg")),
                new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+"上海哔哩哔哩"+File.separator+File.separator+"HII.jpg").getName());}}).toString());
        System.out.println(new AutoDetectParser().getDetector().detect(new BufferedInputStream(new FileInputStream(RESUME_FILE_STORE_PATH_PREFIX+File.separator+"苏杉杉"+File.separator+File.separator+"新-FR3阶段测试卷答案.pdf")), new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, new File(RESUME_FILE_STORE_PATH_PREFIX+File.separator+"苏杉杉"+File.separator+File.separator+"新-FR3阶段测试卷答案.pdf").getName());}}).toString());
        Pattern pattern = Pattern.compile("image/.*");
//        Matcher m = pattern.matcher(new AutoDetectParser().getDetector().detect(new BufferedInputStream(new FileInputStream(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+"上海哔哩哔哩"+File.separator+File.separator+"HII.jpg")), new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+"上海哔哩哔哩"+File.separator+File.separator+"HII.jpg").getName());}}).toString());
        Matcher m = pattern.matcher(new AutoDetectParser().getDetector().detect(new BufferedInputStream(new FileInputStream(RESUME_FILE_STORE_PATH_PREFIX+File.separator+"刘姝贤"+File.separator+File.separator+"4-推优登记表.doc")), new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, new File(RESUME_FILE_STORE_PATH_PREFIX+File.separator+"刘姝贤"+File.separator+File.separator+"4-推优登记表.doc").getName());}}).toString());
        System.out.println("*****************");
        System.out.println(m.matches());
        System.out.println(new Tika().detect(new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+"上海哔哩哔哩"+File.separator+File.separator+"HII.jpg")));
        System.out.println(new Tika().detect(new File(RESUME_FILE_STORE_PATH_PREFIX+File.separator+"苏杉杉"+File.separator+File.separator+"新-FR3阶段测试卷答案.pdf")));
//        System.out.println(pattern.matcher(new Tika().detect(new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+"上海哔哩哔哩"+File.separator+File.separator+"HII.jpg"))).matches());
//        System.out.println(Pattern.compile("image/.*").matcher(new Tika().detect(new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+"上海哔哩哔哩"+File.separator+File.separator+"HII.jpg"))).matches());
//        System.out.println("*****************");
//        System.out.println(PDF_PATTERN.matcher(new Tika().detect(new File(RESUME_FILE_STORE_PATH_PREFIX+File.separator+"苏杉杉"+File.separator+File.separator+"新-FR3阶段测试卷答案.pdf"))).matches());
    }


    @Test
    void dataSource() throws SQLException {
        System.out.println(dataSource.getClass());

        System.out.println(dataSource.getConnection());

        System.out.println(new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+"上海哔哩哔哩"+File.separator+File.separator+"HII.jpg").getName());
        dataSource.getConnection().close();
    }//在测试类里面不能用awt


//    @Test
//    void mapTextLoad(){
////        Map<String,Object> tm=new HashMap<String, Object>();
////        tm.put("a",123);
////        tm.put("a",456);
////        System.out.println(tm.get("a"));
////        System.out.println(tm.size());
//        System.out.println(System.currentTimeMillis());
//        int a=0;
//        while(a<1000000){
//            a++;
//        }
//        System.out.println(System.currentTimeMillis());
//
//    }


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
