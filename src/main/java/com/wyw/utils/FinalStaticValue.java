package com.wyw.utils;

import java.util.regex.Pattern;

/**
 * @author 鱼酥不是叔
 */
public class FinalStaticValue {




    public static final int DELETED_FLAG=-1;

    public static String PHONE_REGEX="^\\d{11}$";
    public static String IDENTITY_REGEX="^[1-9]([0-9]{16}|[0-9]{13})[xX0-9]$";
    public static String EMAIL_REGEX="^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";


    public static final String DEFAULT_MAIL_SENDER="1260518606@qq.com";
    public static final String ID_SUBJECT="获取您注册的账号id";
    public static final String ID_TEXT="您的帮帮网登录id为：";
    public static final String PASSWORD_SUBJECT="获取您注册的账号密码";
    public static final String PASSWORD_TEXT="您的帮帮网登录密码为:";
    public static final String ADMIN_REGISTER_SUBJECT ="获得新管理员的账号密码";
    public static final String ADMIN_REGISTER_TEXT_ACCOUNT="系统生成的管理员账号为：           ";
    public static final String ADMIN_REGISTER_TEXT_PASSWORD="系统生成的管理员密码为：";
    public static final String ADMIN_PASSWORD_SUBJECT="获得管理员的密码：";
    public static final String ADMIN_PASSWORD_TEXT ="管理员密码为：";

    public static final String LOW_STR = "abcdefghijklmnopqrstuvwxyz";
    public static final String SPECIAL_STR = "~!@#$%^&*()_+/-=[]{};:'<>?.";
    public static final String NUM_STR = "0123456789";


    public static final int INT_FEMALE=1;
    public static final int INT_MALE=0;
    public static final String STR_FEMALE="男";
    public static final String STR_MALE="女";

    public static final String STR_STUDENT="学生";
    public static final int INT_STUDENT=0;
    public static final String STR_COMPANY="公司";
    public static final int INT_COMPANY=1;
    public static final String STR_ADMIN="管理员";
    public static final int INT_ADMIN=2;







    public static final int SUCCESS =0;
    public static final int EMPTY_ACCOUNT =1;
    public static final int EMPTY_PASSWORD =2;
    public static final int EMPTY_USER =3;
    public static final int ERROR_PASSWORD =4;
    public static final int EMPTY_POJO=5;
    public static final int ILLEGAL_INPUT_SALARY_NUM=6;
    public static final int ILLEGAL_INPUT_EXPERIENCE_NUM=7;
    public static final int ILLEGAL_INPUT_AGE_NUM=8;
    public static final int ILLEGAL_INPUT_EXPENSE=9;
    public static final int INCONSISTENT_PASSWORD=10;
    public static final int ERROR_PHONE_NUMBER=11;
    public static final int ERROR_IDENTITY_NUMBER=12;
    public static final int ERROR_EMAIL=13;
    public static final int REPEATED_PHONE=14;
    public static final int REPEATED_EMAIL=15;
    public static final int REPEATED_IDENTITY=16;
    public static final int ERROR_CHECK_CODE=17;

    public static final int TODAY =0;
    public static final int YESTERDAY =1;
    public static final int THE_DAY_BEFORE_YESTERDAY=2;
    public static final int TWO_DAYS_BEFORE_YESTERDAY=3;
    public static final int WEEK=7;
    public static final int MONTH=30;
    public static final int YEAR=365;

    public static final int HOMEPAGE_IMG_CAPACITY=4;

    public static final String RESUME_FILE_STORE_PATH_PREFIX ="stuResume";
    public static final String COMPANY_FILE_STORE_PATH_PREFIX ="comImg";

    public static final String STRING_NULL ="";
    public static final Integer INTEGER_NULL=0;
    public static final Long LONG_NULL=0L;


    public static final int EAT=1;
    public static final int WEAR=2;
    public static final int LIVE=3;
    public static final int TRANSPORTATION=4;
    public static final int ENTERTAINMENT=5;
    public static final String HOT_PORT ="火锅类";
    public static final String SPECIAL_DISH ="特色菜系";
    public static final String PUBLIC_DELICIOUS="大众美食";
    public static final String SMALL_EAT="风味小吃";
    public static final String SPRING_WEATHER="春装";
    public static final String SUMMER_WEATHER="夏装";
    public static final String AUTUMN_WEATHER="秋装";
    public static final String WINTER_WEATHER="冬装";
    public static final String HOUSE="房屋";
    public static final String ARCHITECTURE_MATERIAL="建材";
    public static final String BICYCLE="骑行";
    public static final String DRIVE_SELF="自驾";
    public static final String VIEW_INTEREST="景点";
    public static final String SING="嗨歌";
    public static final String SPORT="运动";



    public static final int CHORE_WORKER=1;
    public static final int PIPELINE_WORKER=2;
    public static final int ELECTRICITY_WORKER=3;
    public static final int WOODEN_WORKER=4;
    public static final int CLEAN_WORKER=5;
    public static final String PAVE_FLOOR_TILE = "地板、面砖的铺设";
    public static final String WHITE_WASH_WORKER = "粉刷工";
    public static final String PAINTER_WORKER = "油漆工";
    public static final String PAVE_FLOOR_HEATING = "地暖的铺设";
    public static final String DESIGN_INSTALL_DRAINAGE_ROAD = "给、排水路的设计安装";
    public static final String INSTALL_URBAN_HEATING = "城市供暖入户安装";
    public static final String DESIGN_INSTALL_LINE = "线路设计安装";
    public static final String INSTALL_SWITCH_SOCKET = "开关、插座的安装";
    public static final String INSTALL_LAMP = "灯具的安装";
    public static final String TRANSPORT_INSTALL_ELECTRICAL_APPLIANCE = "电器的运送、安装";
    public static final String DESIGN_CONSTRUCT_DECORATION = "装修设计施工";
    public static final String INSTALL_ACCESSORY_DECORATION = "附属装饰安装";
    public static final String INSTALL_VARIOUS_CATEGORY = "各种门类安装";
    public static final String TRANSPORT_INSTALL_FURNITURE= "家具的运送、安装";
    public static final String CLEAN_HOUSEHOLD_INDOOR= "家居室内清洁";
    public static final String ODD_JOB= "零工";


    public static final Integer ACCEPT_STATUS =10;
    public static final Integer REFUSE_STATUS =20;
    public static final Integer UN_DISPOSED_STATUS =30;
    public static final Integer DEPRECATED_STATUS=40;

    public static final int PRIMARY_SCHOOL_DEGREE =10;
    public static final int JUNIOR_HIGH_SCHOOL_DEGREE =20;
    public static final int HIGH_SCHOOL_DEGREE =30;
    public static final int TERTIARY_DEGREE =40;
    public static final int UNDERGRADUATE_DEGREE =50;
    public static final int MASTER_DEGREE =60;
    public static final int DOCTOR_DEGREE =70;

    public static final String S_PRIMARY_SCHOOL_DEGREE ="小学";
    public static final String S_JUNIOR_HIGH_SCHOOL_DEGREE ="初中";
    public static final String S_HIGH_SCHOOL_DEGREE ="高中";
    public static final String S_TERTIARY_DEGREE ="大专";
    public static final String S_UNDERGRADUATE_DEGREE ="本科";
    public static final String S_MASTER_DEGREE ="硕士";
    public static final String S_DOCTOR_DEGREE ="博士";

    public static final int SAVE_COUNT=5;
    public static final long SAVE_BETWEEN_TIME=1000L;

    public static final Pattern IMG_PATTERN= Pattern.compile("image/.*");

    public static final Pattern DOC_PATTERN=Pattern.compile("application/msword");

    public static final Pattern DOCX_PATTERN=Pattern.compile("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    public static final Pattern PDF_PATTERN=Pattern.compile("application/pdf");

    public static final String TABLE_ADMIN="Admin";
    public static final String TABLE_APPLYLOCALINFORMATION="ApplyLocalInformation";
    public static final String TABLE_APPLYPARTTIMEJOB="ApplyPartTimeJob";
    public static final String TABLE_APPLYSERVICEPROVIDE="ApplyServiceProvide";
    public static final String TABLE_COMPANY="Company";
    public static final String TABLE_FILECOMPANY="FileCompany";
    public static final String TABLE_LOCALINFORMATION="LocalInformation";
    public static final String TABLE_PARTTIMEJOB="PartTimeJob";
    public static final String TABLE_SERVICEPROVIDE="ServiceProvide";
    public static final String TABLE_SOCKETCHAT="SocketChat";
    public static final String TABLE_STUDENT="Student";
    public static final String TABLE_WEBADVICE="WebAdvice";

    public static final Integer PAGE_SIZE_FIVE=5;
    public static final Integer PAGE_SIZE_THREE=3;

    public static final Long REDIS_SCAN_COUNT=9999L;

    public static final Integer DEFAULT_PAGE_COUNT=1;

}
