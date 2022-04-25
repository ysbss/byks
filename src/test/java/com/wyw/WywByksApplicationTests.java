package com.wyw;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class WywByksApplicationTests {

    @Test
    void contextLoads() throws ParseException, FileNotFoundException {
        String abc="stuResume"+ File.separator+"胡晓慧"+File.separator+"新建 Microsoft PowerPoint 演示文稿.pptx";

        File file = new File(abc);
        System.out.println(abc);
        System.out.println(abc.indexOf(File.separator));
        System.out.println(abc.substring(abc.lastIndexOf(File.separator)+1, abc.length()));

    }

}
