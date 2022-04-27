package com.wyw.utils;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wyw.pojo.*;
import com.wyw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wyw.utils.FinalStaticValue.*;

/**
 * @author WYW
 */
@Component
public class Util {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private StudentService studentService;

    @Resource
    private CompanyService companyService;

    @Resource
    private AdminService adminService;

    public int isLegalInputLogin(Long Account, String Password, Student student, Model model) {

        if (Account == null) {
            return FinalStaticValue.EMPTY_ACCOUNT;
        }
        if (Password == null || Password.equals("")) {
            return FinalStaticValue.EMPTY_PASSWORD;
        }

        if (student == null) {
            return FinalStaticValue.EMPTY_USER;
        }
        if (!Password.equals(student.getSPassword())) {
            model.addAttribute("sMsg", "输入的密码错误");
            return FinalStaticValue.ERROR_PASSWORD;
        }
        return FinalStaticValue.SUCCESS;
    }

    public int isLegalInputLogin(String Account, String Password, Company company, Model model) {
        if (Account == null || Account.length() == 0) {
            return FinalStaticValue.EMPTY_ACCOUNT;
        }
        if (Password == null || Password.equals("")) {
            return FinalStaticValue.EMPTY_PASSWORD;
        }
        if (company == null) {
            return FinalStaticValue.EMPTY_USER;
        }
        if (!Password.equals(company.getCPassword())) {
            model.addAttribute("sMsg", "输入的密码错误");
            return FinalStaticValue.ERROR_PASSWORD;
        }
        return FinalStaticValue.SUCCESS;
    }

    public int isLegalInputLogin(Long Account, String Password, Admin admin, Model model) {
        if (Account == null) {
            return FinalStaticValue.EMPTY_ACCOUNT;
        }
        if (Password == null || Password.equals("")) {
            return FinalStaticValue.EMPTY_PASSWORD;
        }
        if (admin == null) {
            return FinalStaticValue.EMPTY_USER;
        }
        if (!Password.equals(admin.getAPassword())) {
            model.addAttribute("sMsg", "输入的密码错误");
            return FinalStaticValue.ERROR_PASSWORD;
        }
        return FinalStaticValue.SUCCESS;
    }

    public int isLegalInputPartTimeJobMap(Map<String,Object> pagePartTimeJob){
        int pSalary1=0;
        int pSalary2=0;
        int pExperience1=0;
        int pExperience2=0;
        int pAge1=0;
        int pAge2=0;
        for (Object o:pagePartTimeJob.values()
        ) {
            if ("".equals(o)||o==null){
                return EMPTY_POJO;
            }
        }
//        if (!(pagePartTimeJob.get("cEmail").toString().matches(EMAIL_REGEX))){
//            return ERROR_EMAIL;
//        }


        for (Object o :
                pagePartTimeJob.keySet()) {
            if ("pSalary1".equals(o)){
                pSalary1=Integer.parseInt(pagePartTimeJob.get("pSalary1").toString());
            }
            if ("pSalary2".equals(o)){
                pSalary2=Integer.parseInt(pagePartTimeJob.get("pSalary2").toString());
            }
            if ("pExperience1".equals(o)){
                pExperience1=Integer.parseInt(pagePartTimeJob.get("pExperience1").toString());
            }
            if ("pExperience2".equals(o)){
                pExperience2=Integer.parseInt(pagePartTimeJob.get("pExperience2").toString());
            }
            if ("pAge1".equals(o)){
                pAge1=Integer.parseInt(pagePartTimeJob.get("pAge1").toString());
            }
            if ("pAge2".equals(o)){
                pAge2=Integer.parseInt(pagePartTimeJob.get("pAge2").toString());
            }
        }
        if(pSalary1>pSalary2){
            return ILLEGAL_INPUT_SALARY_NUM;
        }
        if(pExperience1>pExperience2){
            return ILLEGAL_INPUT_EXPERIENCE_NUM;
        }
        if(pAge1>pAge2){
            return ILLEGAL_INPUT_AGE_NUM;
        }
        return SUCCESS;
    }

    public int isLegalInputLocalInformationMap(Map<String,Object> pageLocalInformation){
        for (Object o:pageLocalInformation.values()
        ) {
            if ("".equals(o)||o==null){
                return EMPTY_POJO;
            }
        }
//        if (!(pageLocalInformation.get("cEmail").toString().matches(EMAIL_REGEX))){
//            return ERROR_EMAIL;
//        }
        return SUCCESS;
    }

    public int isLegalInputServiceProvideMap(Map<String,Object> pageServiceProvide){
        int spExpense1=0;
        int spExpense2=0;
        System.out.println("我进来了判断expense");
        for (Object o:pageServiceProvide.values()
        ) {
            if ("".equals(o)||o==null){
                return EMPTY_POJO;
            }
        }
//        if (!(pageServiceProvide.get("cEmail").toString().matches(EMAIL_REGEX))){
//            return ERROR_EMAIL;
//        }
        for (Object o: pageServiceProvide.keySet()
             ) {
            if ("spExpense1".equals(o)){
                spExpense1=Integer.parseInt(pageServiceProvide.get("spExpense1").toString());
            }
            if ("spExpense2".equals(o)){
                spExpense2=Integer.parseInt(pageServiceProvide.get("spExpense2").toString());
            }
        }
        if (spExpense1>spExpense2){
            return ILLEGAL_INPUT_EXPENSE;
        }
        return SUCCESS;
    }

    public int isLegalInputWebServiceMap(Map<String,Object> pageWebAdvice){
        for (Object o:pageWebAdvice.values()
        ) {
            if ("".equals(o)||o==null){
                return EMPTY_POJO;
            }
        }
        return SUCCESS;
    }

    public int isLegalInputStudentMap(Map<String,Object> pageStudent,Long sId){

        String sPassword1 ="";
        String sPassword2 ="";


        for (Object o:pageStudent.values()
        ) {
            if ("".equals(o)||o==null){
                return EMPTY_POJO;
            }
        }
        for (Object o: pageStudent.keySet()
        ) {
            if ("sPassword1".equals(o)){
                sPassword1=pageStudent.get("sPassword1").toString();
            }
            if ("sPassword2".equals(o)){
                sPassword2=pageStudent.get("sPassword2").toString();
            }
        }
        if (!(sPassword1.equals(sPassword2))){
            return INCONSISTENT_PASSWORD;
        }
        if (pageStudent.get("sPhoneNumber")!=null){
            if (!(pageStudent.get("sPhoneNumber").toString().matches(PHONE_REGEX))){
                return ERROR_PHONE_NUMBER;
            }
        }
        if (pageStudent.get("sIdentityNum") != null) {
            if (!(pageStudent.get("sIdentityNum").toString().matches(IDENTITY_REGEX))){
                return ERROR_IDENTITY_NUMBER;
            }
        }

        if (pageStudent.get("sEmail")!=null){
            if (!(pageStudent.get("sEmail").toString().matches(EMAIL_REGEX))){
                return ERROR_EMAIL;
            }
        }
        if (pageStudent.get("sPhoneNumber")!=null){
            Map<String, Object> repeatedMap1 = new HashMap<>();
            repeatedMap1.put("sPhoneNumber",pageStudent.get("sPhoneNumber").toString());
            List<Student> judgeStudentList = studentService.fetchStusList(repeatedMap1);
            if (!(judgeStudentList.isEmpty())&&!(judgeStudentList.get(INTEGER_NULL).getSId().equals(sId))){
                return REPEATED_PHONE;
            }
        }
        if (pageStudent.get("sEmail")!=null) {
            Map<String, Object> repeatedMap2 = new HashMap<>();
            repeatedMap2.put("sEmail",pageStudent.get("sEmail").toString());
            List<Student> judgeStudentList = studentService.fetchStusList(repeatedMap2);

            if (!(judgeStudentList.isEmpty())&&!(judgeStudentList.get(INTEGER_NULL).getSId().equals(sId))){
                return REPEATED_EMAIL;
            }
        }

        if (pageStudent.get("sIdentityNum")!=null) {
            Map<String, Object> repeatedMap3 = new HashMap<>();
            repeatedMap3.put("sIdentityNum",pageStudent.get("sIdentityNum").toString());
            List<Student> judgeStudentList = studentService.fetchStusList(repeatedMap3);

            if (!(judgeStudentList.isEmpty())&&!(judgeStudentList.get(INTEGER_NULL).getSId().equals(sId))){
                return REPEATED_IDENTITY;
            }
        }


        return SUCCESS;
    }

    public int isLegalInputCompanyMap(Map<String,Object> pageCompany,Long cId){
        String cPassword1 ="";
        String cPassword2 ="";


        for (Object o:pageCompany.values()
        ) {
            if ("".equals(o)||o==null){
                return EMPTY_POJO;
            }
        }
        for (Object o: pageCompany.keySet()
        ) {
            if ("cPassword1".equals(o)){
                cPassword1=pageCompany.get("cPassword1").toString();
            }
            if ("cPassword2".equals(o)){
                cPassword2=pageCompany.get("cPassword2").toString();
            }
        }
        if (!(cPassword1.equals(cPassword2))){
            return INCONSISTENT_PASSWORD;
        }

        if (pageCompany.get("cEmail")!=null){
            if (!(pageCompany.get("cEmail").toString().matches(EMAIL_REGEX))){
                return ERROR_EMAIL;
            }
        }
        if (pageCompany.get("cEmail")!=null) {
            Map<String, Object> repeatedMap2 = new HashMap<>();
            repeatedMap2.put("cEmail",pageCompany.get("cEmail").toString());
            List<Company> judgeCompaniesList = companyService.fetchCompaniesList(repeatedMap2);
            if (!(judgeCompaniesList.isEmpty())&&!(judgeCompaniesList.get(INTEGER_NULL).getCId().equals(cId))){
                return REPEATED_EMAIL;
            }
        }

        return SUCCESS;
    }

    public int isLegalInputAdminMap(Map<String,Object> pageAdmin,String checkCode,Long aId){
        for (Object o:pageAdmin.values()
        ) {
            if ("".equals(o)||o==null){
                return EMPTY_POJO;
            }
        }
        if (pageAdmin.get("aEmail")!=null){
            if (!(pageAdmin.get("aEmail").toString().matches(EMAIL_REGEX))){
                return ERROR_EMAIL;
            }
        }
        if (pageAdmin.get("aEmail")!=null) {
            Map<String, Object> repeatedMap2 = new HashMap<>();
            repeatedMap2.put("aEmail",pageAdmin.get("aEmail").toString());
            List<Admin> judgeCompaniesList = adminService.fetchAdminsList(repeatedMap2);
            if (!(judgeCompaniesList.isEmpty())&&!(judgeCompaniesList.get(INTEGER_NULL).getAId().equals(aId))){
                return REPEATED_EMAIL;
            }
        }
        if(pageAdmin.get("aCheckCode")!=null){
            if (!checkCode.equals(pageAdmin.get("aCheckCode").toString())){
                return ERROR_CHECK_CODE;
            }
        }

        return SUCCESS;
    }


    public Date StringFromDataBaseTransferToDate(String DataBaseDate,SimpleDateFormat simpleDateFormat) throws ParseException {
        //database=Sun Apr 10 00:22:47 CST 2022
        Date date=simpleDateFormat.parse(DataBaseDate);
        return date;
    }

    public String CSTDateFormatFromPageTransferToString(Date PageDateFormat,SimpleDateFormat simpleDateFormat){
        String date=simpleDateFormat.format(PageDateFormat);
        return date;
    }


    public List<Map<String,Object>> addToFourElement(List<Map<String,Object>> mapList){
        int staticLength=HOMEPAGE_IMG_CAPACITY-mapList.size();
        for (int i = 0; i < staticLength; i++) {
            Map<String, Object> tmpMap =mapList.get(i);
            mapList.add(tmpMap);
        }
        return mapList;
    }

    public String computePageDays(String dataBaseDate,SimpleDateFormat simpleDateFormat) throws ParseException {
        Date dataDate=simpleDateFormat.parse(dataBaseDate);
        Date curDate = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()));
        Calendar dataCalendar=Calendar.getInstance();
        Calendar curDateCalendar=Calendar.getInstance();
        dataCalendar.setTime(dataDate);
        curDateCalendar.setTime(curDate);
        long dataTimeMillis = dataCalendar.getTimeInMillis();
        long curDateTimeMillis= curDateCalendar.getTimeInMillis();
        long betweenDays=(curDateTimeMillis-dataTimeMillis)/(1000*3600*24);
        String pageDays="";
        if (betweenDays==TODAY){
            pageDays="今天";
        }
        if (betweenDays==YESTERDAY){
            pageDays="昨天";
        }
        if (betweenDays==THE_DAY_BEFORE_YESTERDAY){
            pageDays="前天";
        }
        if (betweenDays==TWO_DAYS_BEFORE_YESTERDAY){
            pageDays="大前天";
        }
        if (betweenDays>TWO_DAYS_BEFORE_YESTERDAY){
            pageDays="三天前";
        }
        if (betweenDays>Calendar.DAY_OF_WEEK){
             pageDays="一周前";
        }
        if (betweenDays>MONTH){
            pageDays="一月前";
        }
        if (betweenDays>YEAR){
            pageDays="一年前";
        }

        return pageDays;

    }


    public PartTimeJob getPartTimeJobByPageParam(Map<String,Object> pagePartTimeJob) throws ParseException {
        Util util = new Util();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        PartTimeJob addPartTimeJob = new PartTimeJob();
        if (pagePartTimeJob.get("pId")!=null){
            addPartTimeJob.setPId(Long.valueOf(pagePartTimeJob.get("pId").toString()));
        }
        addPartTimeJob.setPName(pagePartTimeJob.get("pName").toString());
        addPartTimeJob.setPPosition(pagePartTimeJob.get("pPosition").toString());
        addPartTimeJob.setPSalary((pagePartTimeJob.get("pSalary1").toString()+"-"+pagePartTimeJob.get("pSalary2").toString()));
        if ((pagePartTimeJob.get("pNum").toString().equals(STRING_NULL))){
            pagePartTimeJob.put("pNum",INTEGER_NULL);
        }
        addPartTimeJob.setPNum(Integer.valueOf(pagePartTimeJob.get("pNum").toString()));

        switch(Integer.parseInt(pagePartTimeJob.get("pEducationalDegree").toString())){
            case PRIMARY_SCHOOL_DEGREE:{
            addPartTimeJob.setPEducationalDegree("小学");break;
            }
            case JUNIOR_HIGH_SCHOOL_DEGREE :{
                addPartTimeJob.setPEducationalDegree("初中");break;
            }
            case HIGH_SCHOOL_DEGREE :{
                addPartTimeJob.setPEducationalDegree("高中");break;
            }
            case TERTIARY_DEGREE :{
                addPartTimeJob.setPEducationalDegree("大专");break;
            }
            case UNDERGRADUATE_DEGREE :{
                addPartTimeJob.setPEducationalDegree("本科");break;
            }
            case MASTER_DEGREE :{
                addPartTimeJob.setPEducationalDegree("硕士");break;
            }
            case DOCTOR_DEGREE :{
                addPartTimeJob.setPEducationalDegree("博士");break;
            }
            default:
        }
            addPartTimeJob.setPExperience((pagePartTimeJob.get("pExperience1").toString()+"-"+pagePartTimeJob.get("pExperience2").toString()));
            addPartTimeJob.setPAge((pagePartTimeJob.get("pAge1").toString()+"-"+pagePartTimeJob.get("pAge2").toString()));
        addPartTimeJob.setPPhoneNumber(pagePartTimeJob.get("pPhoneNumber").toString());
        addPartTimeJob.setPContactPerson(pagePartTimeJob.get("pContactPerson").toString());
        addPartTimeJob.setPSubmitTime(util.CSTDateFormatFromPageTransferToString(sdf.parse(sdf.format(System.currentTimeMillis())),sdf));

        return addPartTimeJob;
    }

    public LocalInformation getLocalInformationByPageParam(Map<String,Object> pageLocalInformation) throws ParseException {
        Util util = new Util();
        switch (pageLocalInformation.get("lSpecificKind").toString().replaceAll("\'","")){
            case HOT_PORT:{pageLocalInformation.put("lKind",EAT);break;}
            case SPECIAL_DISH:{pageLocalInformation.put("lKind",EAT);break;}
            case PUBLIC_DELICIOUS:{pageLocalInformation.put("lKind",EAT);break;}
            case SMALL_EAT:{pageLocalInformation.put("lKind",EAT);break;}
            case SPRING_WEATHER:{pageLocalInformation.put("lKind",WEAR);break;}
            case SUMMER_WEATHER:{pageLocalInformation.put("lKind",WEAR);break;}
            case AUTUMN_WEATHER:{pageLocalInformation.put("lKind",WEAR);break;}
            case WINTER_WEATHER:{pageLocalInformation.put("lKind",WEAR);break;}
            case HOUSE:{pageLocalInformation.put("lKind",LIVE);break;}
            case ARCHITECTURE_MATERIAL:{pageLocalInformation.put("lKind",LIVE);break;}
            case BICYCLE:{pageLocalInformation.put("lKind",TRANSPORTATION);break;}
            case DRIVE_SELF:{pageLocalInformation.put("lKind",TRANSPORTATION);break;}
            case VIEW_INTEREST:{pageLocalInformation.put("lKind",ENTERTAINMENT);break;}
            case SING:{pageLocalInformation.put("lKind",ENTERTAINMENT);break;}
            case SPORT:{pageLocalInformation.put("lKind",ENTERTAINMENT);break;}
            default:
        }


        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        LocalInformation addLocalInformation = new LocalInformation();
        if (pageLocalInformation.get("lId")!=null){
            addLocalInformation.setLId(Long.valueOf(pageLocalInformation.get("lId").toString()));
        }
        addLocalInformation.setLName(pageLocalInformation.get("lName").toString());
        addLocalInformation.setLKind(Integer.valueOf(pageLocalInformation.get("lKind").toString()));
        addLocalInformation.setLSpecificKind(pageLocalInformation.get("lSpecificKind").toString().replaceAll("\'",""));
        addLocalInformation.setLPhoneNumber(pageLocalInformation.get("lPhoneNumber").toString());
        addLocalInformation.setLContactPerson(pageLocalInformation.get("lContactPerson").toString());
        addLocalInformation.setLSubmitTime(util.CSTDateFormatFromPageTransferToString(sdf.parse(sdf.format(System.currentTimeMillis())),sdf));


        return addLocalInformation;
    }

    public ServiceProvide getServiceProvideByPageParam(Map<String,Object> pageServiceProvide) throws ParseException {
        Util util = new Util();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        switch(pageServiceProvide.get("spSpecificKind").toString().replaceAll("\'","")){
            case PAVE_FLOOR_TILE :{pageServiceProvide.put("spKind",CHORE_WORKER);break;}
            case WHITE_WASH_WORKER :{pageServiceProvide.put("spKind",CHORE_WORKER);break;}
            case PAINTER_WORKER :{pageServiceProvide.put("spKind",CHORE_WORKER);break;}
            case PAVE_FLOOR_HEATING :{pageServiceProvide.put("spKind",PIPELINE_WORKER);break;}
            case DESIGN_INSTALL_DRAINAGE_ROAD:{pageServiceProvide.put("spKind",PIPELINE_WORKER);break;}
            case INSTALL_URBAN_HEATING :{pageServiceProvide.put("spKind",PIPELINE_WORKER);break;}
            case DESIGN_INSTALL_LINE :{pageServiceProvide.put("spKind",ELECTRICITY_WORKER);break;}
            case INSTALL_SWITCH_SOCKET :{pageServiceProvide.put("spKind",ELECTRICITY_WORKER);break;}
            case INSTALL_LAMP :{pageServiceProvide.put("spKind",ELECTRICITY_WORKER);break;}
            case TRANSPORT_INSTALL_ELECTRICAL_APPLIANCE:{pageServiceProvide.put("spKind",ELECTRICITY_WORKER);break;}
            case DESIGN_CONSTRUCT_DECORATION :{pageServiceProvide.put("spKind",WOODEN_WORKER);break;}
            case INSTALL_ACCESSORY_DECORATION :{pageServiceProvide.put("spKind",WOODEN_WORKER);break;}
            case INSTALL_VARIOUS_CATEGORY :{pageServiceProvide.put("spKind",WOODEN_WORKER);break;}
            case TRANSPORT_INSTALL_FURNITURE:{pageServiceProvide.put("spKind",WOODEN_WORKER);break;}
            case CLEAN_HOUSEHOLD_INDOOR:{pageServiceProvide.put("spKind",CLEAN_WORKER);break;}
            case ODD_JOB:{pageServiceProvide.put("spKind",CLEAN_WORKER);break;}
            default:
        }
        ServiceProvide addServiceProvide = new ServiceProvide();
        if (pageServiceProvide.get("spId")!=null){
            addServiceProvide.setSpId(Long.valueOf(pageServiceProvide.get("spId").toString()));
        }
        addServiceProvide.setSpName(pageServiceProvide.get("spName").toString());
        addServiceProvide.setSpKind(Integer.valueOf(pageServiceProvide.get("spKind").toString()));
        addServiceProvide.setSpSpecificKind(pageServiceProvide.get("spSpecificKind").toString().replace("\'",""));
        addServiceProvide.setSpExpense(pageServiceProvide.get("spExpense1").toString()+"-"+pageServiceProvide.get("spExpense2").toString());
        addServiceProvide.setSpPhoneNumber(pageServiceProvide.get("spPhoneNumber").toString());
        addServiceProvide.setSpContactPerson(pageServiceProvide.get("spContactPerson").toString());
        addServiceProvide.setSpSubmitTime(util.CSTDateFormatFromPageTransferToString(sdf.parse(sdf.format(System.currentTimeMillis())),sdf));
        return  addServiceProvide;
    }

    public Map<String,Object> getPagePartTimeJobFromMap(Map<String, Object> PartTimeJob){

        System.out.println(PartTimeJob.get("pSalary").toString());
        PartTimeJob.put("pSalary1",PartTimeJob.get("pSalary").toString().split("\\-")[0]);
        PartTimeJob.put("pSalary2",PartTimeJob.get("pSalary").toString().split("\\-")[1]);
        PartTimeJob.put("pExperience1",PartTimeJob.get("pExperience").toString().split("\\-")[0]);
        PartTimeJob.put("pExperience2",PartTimeJob.get("pExperience").toString().split("\\-")[1]);
        PartTimeJob.put("pAge1",PartTimeJob.get("pAge").toString().split("\\-")[0]);
        PartTimeJob.put("pAge2",PartTimeJob.get("pAge").toString().split("\\-")[1]);
        if (S_PRIMARY_SCHOOL_DEGREE.equals(PartTimeJob.get("pEducationalDegree").toString())){
            PartTimeJob.put("pEducationalDegree",PRIMARY_SCHOOL_DEGREE);
        }
        if (S_JUNIOR_HIGH_SCHOOL_DEGREE.equals(PartTimeJob.get("pEducationalDegree").toString())){
            PartTimeJob.put("pEducationalDegree",JUNIOR_HIGH_SCHOOL_DEGREE);
        }
        if (S_HIGH_SCHOOL_DEGREE.equals(PartTimeJob.get("pEducationalDegree").toString())){
            PartTimeJob.put("pEducationalDegree",HIGH_SCHOOL_DEGREE);
        }
        if (S_TERTIARY_DEGREE.equals(PartTimeJob.get("pEducationalDegree").toString())){
            PartTimeJob.put("pEducationalDegree",TERTIARY_DEGREE);
        }
        if (S_UNDERGRADUATE_DEGREE.equals(PartTimeJob.get("pEducationalDegree").toString())){
            PartTimeJob.put("pEducationalDegree",UNDERGRADUATE_DEGREE);
        }
        if (S_MASTER_DEGREE.equals(PartTimeJob.get("pEducationalDegree").toString())){
            PartTimeJob.put("pEducationalDegree",MASTER_DEGREE);
        }
        if (S_DOCTOR_DEGREE.equals(PartTimeJob.get("pEducationalDegree").toString())){
            PartTimeJob.put("pEducationalDegree",DOCTOR_DEGREE);
        }

        return PartTimeJob;
    }

    public Map<String,Object> getPageServiceProvideJobFromMap(Map<String, Object> serviceProvide){
        serviceProvide.put("spExpense1",serviceProvide.get("spExpense").toString().split("\\-")[0]);
        serviceProvide.put("spExpense2",serviceProvide.get("spExpense").toString().split("\\-")[1]);
        return serviceProvide;
    }

    public void sendMail(String subject,String text,String from,String to){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from);
        javaMailSender.send(simpleMailMessage);

    }

}
