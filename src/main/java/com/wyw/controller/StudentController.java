package com.wyw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyw.pojo.FileResume;
import com.wyw.pojo.Student;
import com.wyw.service.*;
import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wyw.utils.FinalStaticValue.*;

/**
 * @author WYW
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    @Qualifier("StudentsServiceImpl")
    StudentService studentService;

    @Autowired
    @Qualifier("PartTimeJobServiceImpl")
    PartTimeJobService partTimeJobService;

    @Resource
    ServiceProvideService serviceProvideService;

    @Resource
    LocalInformationService localInformationService;

    @Resource
    ApplyLocalInformationService applyLocalInformationService;

    @Resource
    ApplyPartTimeJobService applyPartTimeJobService;

    @Resource
    ApplyServiceProvideService applyServiceProvideService;

    @Resource
    FileResumeService fileResumeService;

    @RequestMapping("/studentLogin")
    public String stuLogin(@RequestParam(value = "sId",required = false) Long sId,
                           @RequestParam( value = "sPassword",required = false) String sPassword,
                           HttpSession session,
                           Model model) throws ParseException {
        Student student = studentService.fetchStuById(sId);
        Util util = new Util();
        int legalInputLoginFlag = util.isLegalInputLogin(sId,sPassword,student,model);
        List<Map<String, Object>> allSpecialPartTimeJobs = partTimeJobService.getAllPageSpecialPartTimeJobs();
        List<Map<String, Object>> latestSpcPartTimeJobs = partTimeJobService.fetchLatestSpcPartTimeJob();

        List<Integer> serviceProvideKinds = serviceProvideService.fetchServiceProvideKinds();
        List<Map<String, Object>> serviceProvideSpecificKinds = serviceProvideService.fetchServiceProvideSpecificKinds();

        List<Integer> localInformationKinds = localInformationService.fetchLocalInformationKinds();
        List<Map<String, Object>> localInformationSpecificKinds = localInformationService.fetchLocalInformationSpecificKinds();



        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        for (Map m:allSpecialPartTimeJobs
        ) {
            m.put("pSubmitTime", util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
        }
        for (Map m:latestSpcPartTimeJobs
        ) {
            m.put("pSubmitTime", util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
        }
        switch (legalInputLoginFlag){
            case FinalStaticValue.EMPTY_ACCOUNT:{
                System.out.println("学生账号空");
                model.addAttribute("sMsg","输入的用户名为空");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
                return "index";
            }
            case FinalStaticValue.EMPTY_PASSWORD:{
                System.out.println("学生密码空");
                model.addAttribute("sMsg","输入的密码为空");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
                return "index";
            }
            case FinalStaticValue.EMPTY_USER:{
                System.out.println("没有此用户");
                model.addAttribute("sMsg","没有此用户");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);

                return "index";
            }
            case FinalStaticValue.ERROR_PASSWORD:{
                System.out.println("学生密码错");
                model.addAttribute("sMsg","输入的密码错误");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);

                return "index";
            }
            default:{
                session.setAttribute("currentName",student.getSName());
                session.setAttribute("currentStuId",student.getSId());
                System.out.println("我进来了学生");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);


                model.addAttribute("loginKind","学生");
                session.setAttribute("loginKind","学生");
                return "index_1";
            }
        }
//        if (legalInputLoginFlag== FinalStaticValue.emptyAccount){
//            model.addAttribute("sMsg","输入的用户名为空");
//            return "index";
//        }
//        if (legalInputLoginFlag==FinalStaticValue.emptyPassword){
//            model.addAttribute("sMsg","输入的密码为空");
//            return "index";
//        }
//        if (legalInputLoginFlag==FinalStaticValue.emptyUser){
//            model.addAttribute("sMsg","没有此用户");
//            return "index";
//        }
//        if (legalInputLoginFlag==FinalStaticValue.errorPassword) {
//            model.addAttribute("sMsg","输入的密码错误");
//            return "index";
//        }

    }

    @RequestMapping("/fetchStusList/{pageNum}")
    public String fetchStusList(@RequestParam(required = false) String pageStuSearchInfo,
                                Model model,
                                HttpSession session,
                                @PathVariable(value = "pageNum",required = false) Integer pageNum){

        PageHelper.startPage(pageNum,3);
        String curStuSearchInfo = session.getAttribute("curStuSearchInfo").toString();
        if (pageStuSearchInfo!=null){
            curStuSearchInfo=pageStuSearchInfo;
            session.setAttribute("curStuSearchInfo",pageStuSearchInfo);
        }
        Map<String,Object> student=new HashMap<String,Object>();
        student.put("stuSearchInfo",curStuSearchInfo);
        List<Student> allStusList = studentService.fetchStusList(student);
        PageInfo<Student> allStusListPageInfo=new  PageInfo<Student>(allStusList);
        model.addAttribute("allStusList",allStusList);
        model.addAttribute("allStusListPageInfo",allStusListPageInfo);
        return "allStusList";
    }

    @RequestMapping("/deleteStu/{pageNum}")
    public String deleteStu(@RequestParam(required = false) String pageStuSearchInfo,
                            Model model,
                            HttpSession session,
                            @RequestParam(required = false)String pageSid,
                            @PathVariable(value = "pageNum",required = false) Integer pageNum){

        Map<String,Object> updateStudent=new HashMap<String,Object>();
        updateStudent.put("sId",pageSid);
        updateStudent.put("sFlag",FinalStaticValue.DELETED_FLAG);
        studentService.updateStudent(updateStudent);

        Map<String, Object> comApplyLocalInformationDealMap = new HashMap<String, Object>();
        comApplyLocalInformationDealMap.put("aliSid",pageSid);
        comApplyLocalInformationDealMap.put("aliStatus",DEPRECATED_STATUS);
        applyLocalInformationService.comDealApplyLocalInformation(comApplyLocalInformationDealMap);


        Map<String, Object> comApplyPartTimeJobDealMap = new HashMap<String, Object>();
        comApplyPartTimeJobDealMap.put("apSid",pageSid);
        comApplyPartTimeJobDealMap.put("apStatus",DEPRECATED_STATUS);
        applyPartTimeJobService.comDealApplyPartTimeJob(comApplyPartTimeJobDealMap);


        Map<String, Object> comApplyServiceProvideDealMap = new HashMap<String, Object>();
        comApplyServiceProvideDealMap.put("aspSid",pageSid);
        comApplyServiceProvideDealMap.put("aspStatus",DEPRECATED_STATUS);
        applyServiceProvideService.comDealApplyServiceProvide(comApplyServiceProvideDealMap);


        /**
         * 这里可以写将文件给删除的方法
         * */
        Map<String, Object> fileResume = new HashMap<String, Object>();
        fileResume.put("fFileFlag",DELETED_FLAG);
        fileResume.put("fFileSid",pageSid);
        fileResumeService.updateFileResume(fileResume);

        /**
         * 去companycontroller抄 不过删的话直接把文件夹删除即可
         * */





        PageHelper.startPage(pageNum,3);
        String curStuSearchInfo = session.getAttribute("curStuSearchInfo").toString();
        if (pageStuSearchInfo!=null && !("".equals(pageStuSearchInfo))){
            curStuSearchInfo=pageStuSearchInfo;
            session.setAttribute("curStuSearchInfo",pageStuSearchInfo);
        }
        Map<String,Object> student=new HashMap<String,Object>();
        student.put("stuSearchInfo",curStuSearchInfo);
        List<Student> allStusList = studentService.fetchStusList(student);
        PageInfo<Student> allStusListPageInfo=new  PageInfo<Student>(allStusList);
        model.addAttribute("allStusList",allStusList);
        model.addAttribute("allStusListPageInfo",allStusListPageInfo);
        return "allStusList";
    }

    @RequestMapping("/updateStu/{sId}")
    public String updateStu(
            @RequestParam Map<String ,Object> pageUpdateStu,
            @PathVariable(value = "sId") Long sId,
            Model model){
        switch (new Util().isLegalInputStudentMap(pageUpdateStu)) {
            case EMPTY_POJO:{Student studentInfo = studentService.fetchStuById(sId);
                Map<String,Object> fileResume=new HashMap<String,Object>();
                fileResume.put("fFileSid",sId);
                model.addAttribute("stuInfo",studentService.fetchStuById(sId));
                model.addAttribute("fileResumes",fileResumeService.fetchFileResumesList(fileResume)); model.addAttribute("updateMsg","请填写完整信息");return "stuUpdate"; }
            case ERROR_PHONE_NUMBER:{Student studentInfo = studentService.fetchStuById(sId);
                Map<String,Object> fileResume=new HashMap<String,Object>();
                fileResume.put("fFileSid",sId);
                model.addAttribute("stuInfo",studentService.fetchStuById(sId));
                model.addAttribute("fileResumes",fileResumeService.fetchFileResumesList(fileResume));model.addAttribute("updateMsg","错误的手机号");return "stuUpdate";}
            case ERROR_IDENTITY_NUMBER:{Student studentInfo = studentService.fetchStuById(sId);
                Map<String,Object> fileResume=new HashMap<String,Object>();
                fileResume.put("fFileSid",sId);
                model.addAttribute("stuInfo",studentService.fetchStuById(sId));
                model.addAttribute("fileResumes",fileResumeService.fetchFileResumesList(fileResume));model.addAttribute("updateMsg","错误的身份证号码");return "stuUpdate";}
            default:
        }
        pageUpdateStu.put("sId",sId);
        studentService.updateStudent(pageUpdateStu);

        Map<String,Object> updFileStorePath=new HashMap<String,Object>();
        Map<String,Object> updFileResume=new HashMap<String,Object>();
        updFileResume.put("fFileSid",sId);
        List<FileResume> updateFileResumes = fileResumeService.fetchFileResumesList(updFileResume);

        updFileStorePath.put("fFileSid",sId);
        if (pageUpdateStu.get("sName") != null) {
            for (FileResume fr: updateFileResumes
                 ) {
                updFileStorePath.put("fFileStorePath",RESUME_FILE_STORE_PATH_PREFIX+File.separator+(pageUpdateStu.get("sName").toString())+File.separator+(fr.getFFileStorePath().substring(fr.getFFileStorePath().lastIndexOf(File.separator)+1,fr.getFFileStorePath().length())));
            }
        }
//        RESUME_FILE_STORE_PATH_PREFIX+File.separator+(pageUpdateStu.get("sName").toString())+File.separator

        fileResumeService.updateFileResume(updFileStorePath);


        Student studentInfo = studentService.fetchStuById(sId);
        Map<String,Object> fileResume=new HashMap<String,Object>();
        fileResume.put("fFileSid",sId);
        model.addAttribute("stuInfo",studentService.fetchStuById(sId));
        model.addAttribute("fileResumes",fileResumeService.fetchFileResumesList(fileResume));
        model.addAttribute("updateMsg","编辑成功");
        return "stuUpdate";
    }

    @RequestMapping("/downLoadFileResume/{sId}/{fFileID}")
    public String downLoadFileResume(@PathVariable(value = "sId") Long sId,
                                     HttpServletResponse httpServletResponse,
                                     @PathVariable(value = "fFileID") String fFileID,
                                     Model model) throws IOException {


        FileResume downLoadFileResume = fileResumeService.fetchFileResumeByfId(fFileID);
        System.out.println("文件uuid："+fFileID);
        httpServletResponse.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(downLoadFileResume.getFFileName(),"UTF-8"));
        FileInputStream fis=new FileInputStream(downLoadFileResume.getFFileStorePath());
        int len=0;
        byte[] buffer=new byte[1024];
        ServletOutputStream os = httpServletResponse.getOutputStream();
        while ((len=fis.read(buffer))>0){
            os.write(buffer,0,len);
        }
        fis.close();
        os.close();


        Student studentInfo = studentService.fetchStuById(sId);
        Map<String,Object> fileResume=new HashMap<String,Object>();
        fileResume.put("fFileSid",sId);
        model.addAttribute("stuInfo",studentInfo);
        model.addAttribute("fileResumes",fileResumeService.fetchFileResumesList(fileResume));
        return "stuUpdate";
    }

    @RequestMapping("/uploadFileResume/{sId}/{fFileID}")
    public String uploadFileResume(MultipartFile pageFileResume,
                                   @PathVariable(value = "sId")Long sId,
                                   @PathVariable(value = "fFileID") String fFileID,
                                   Model model) throws IOException {

        FileResume repeatedFileResume = fileResumeService.fetchFileResumeByfId(fFileID);
        Student studentInfo = studentService.fetchStuById(sId);

            Map<String,Object> updFileResume=new HashMap<String,Object> ();
            File storeNewFile=new File(RESUME_FILE_STORE_PATH_PREFIX+File.separator+studentInfo.getSName()+File.separator);
            File oldFile=new File(repeatedFileResume.getFFileStorePath());
            System.out.println(oldFile.getAbsolutePath());
            if (oldFile.delete()){
                System.out.println("删除成功");

            }else {
                System.out.println("删除失败");
                Map<String,Object> fileResume=new HashMap<String,Object>();
                fileResume.put("fFileSid",sId);
                model.addAttribute("stuInfo",studentInfo);
                model.addAttribute("fileResumes",fileResumeService.fetchFileResumesList(fileResume));
                model.addAttribute("uploadMsg","上传失败");
                return "stuUpdate";

            }
            if (!storeNewFile.exists()){
                storeNewFile.mkdirs();
            }
            pageFileResume.transferTo(new File(storeNewFile.getAbsoluteFile()+File.separator+pageFileResume.getOriginalFilename()));


            updFileResume.put("fFileName",pageFileResume.getOriginalFilename());
            updFileResume.put("fFileStorePath",RESUME_FILE_STORE_PATH_PREFIX+File.separator+studentInfo.getSName()+File.separator+pageFileResume.getOriginalFilename());


            fileResumeService.updateFileResume(updFileResume);




        Map<String,Object> fileResume=new HashMap<String,Object>();
        fileResume.put("fFileSid",sId);
        model.addAttribute("stuInfo",studentInfo);
        model.addAttribute("fileResumes",fileResumeService.fetchFileResumesList(fileResume));
        model.addAttribute("uploadMsg","上传成功");
        return "stuUpdate";
    }





    @RequestMapping("/addStu")
    public String addStu(@RequestParam Map<String ,Object> pageRegisterStu,
                         Model model) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        switch (new Util().isLegalInputStudentMap(pageRegisterStu)) {
            case EMPTY_POJO:{ model.addAttribute("registerMsg","请填写完整信息");return "registerStu"; }
            case INCONSISTENT_PASSWORD:{model.addAttribute("registerMsg","密码输入不一致");return "registerStu";}
            case ERROR_PHONE_NUMBER:{model.addAttribute("registerMsg","错误的手机号");return "registerStu";}
            case ERROR_IDENTITY_NUMBER:{model.addAttribute("registerMsg","错误的身份证号码");return "registerStu";}
            default:
        }
        pageRegisterStu.put("sId",FinalStaticValue.INTEGER_NULL);
        pageRegisterStu.put("sRegisterTime",new Util().CSTDateFormatFromPageTransferToString(sdf.parse(sdf.format(System.currentTimeMillis())),sdf));
        pageRegisterStu.put("sPassword",pageRegisterStu.get("sPassword1").toString());

        studentService.addStudent(pageRegisterStu);


        model.addAttribute("registerMsg","注册成功，点击左侧按钮或者上方按钮前往登录");
        return "registerStu";
    }



}
