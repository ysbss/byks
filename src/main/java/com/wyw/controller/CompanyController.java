package com.wyw.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.deploy.net.HttpResponse;
import com.wyw.pojo.*;
import com.wyw.service.*;
import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.Util;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.wyw.utils.FinalStaticValue.*;

/**
 * @author WYW
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    @Qualifier("CompanyServiceImpl")
    CompanyService companyService;

    @Autowired
    @Qualifier("PartTimeJobServiceImpl")
    PartTimeJobService partTimeJobService;

    @Resource
    FileResumeService fileResumeService;

    @Resource
    StudentService studentService;

    @Resource
    FileCompanyService fileCompanyService;

    @Resource
    ServiceProvideService serviceProvideService;

    @Resource
    LocalInformationService localInformationService;

    @Resource
    JavaMailSenderImpl javaMailSender;

    @Resource
    Util util;

    @Resource
    ApplyLocalInformationService applyLocalInformationService;
    @Resource
    ApplyPartTimeJobService applyPartTimeJobService;
    @Resource
    ApplyServiceProvideService applyServiceProvideService;


    @RequestMapping("/companyLogin")
    public String companyLogin(@RequestParam(value = "cName",required = false) String cName,
                               @RequestParam(value = "cPassword",required = false) String cPassword,
                               HttpSession session,
                               Model model) throws ParseException {
        Company company = companyService.fetchCompanyByName(cName);

        int legalInputLoginFlag = util.isLegalInputLogin(cName,cPassword,company,model);
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
                System.out.println("公司账号空");
                model.addAttribute("cMsg","输入的公司名为空");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
                return "index";
            }
            case FinalStaticValue.EMPTY_PASSWORD:{
                System.out.println("公司密码空");
                model.addAttribute("cMsg","输入的密码为空");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
                return "index";
            }
            case FinalStaticValue.EMPTY_USER:{
                System.out.println("公司没有此用户");
                model.addAttribute("cMsg","没有此公司");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
                return "index";
            }
            case FinalStaticValue.ERROR_PASSWORD:{
                System.out.println("公司密码错");
                model.addAttribute("cMsg","输入的密码错误");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
                return "index";
            }
            default:{
                session.setAttribute("currentName",company.getCName());
                session.setAttribute("currentComId",company.getCId());
                session.setAttribute("currentChatId",company.getCId());
                System.out.println("我进来了公司");
//                for (Map m:allSpecialPartTimeJob
//                     ) {
//                    System.out.println(m.get("pName"));
//                }
//                System.out.println(allSpecialPartTimeJob.get(1).get("pName"));
//                String pName = "1234我进来了";
//                model.addAttribute("tes",pName);
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);


                model.addAttribute("loginKind","公司");
                session.setAttribute("loginKind","公司");
                return "index_1";
//                return "redirect:/main.html";
            }
        }
    }


    @RequestMapping(value = {"/downLoadStuResume/{sId}/{fFileID}", "/downLoadStuResume/{sId}/{pId}/{fFileID}"})
    public String downLoadStuResume(Model model,
                                    HttpServletResponse httpServletResponse,
                                    @PathVariable(value = "fFileID") String fFileID,
                                    @PathVariable(value = "sId") Long sId,
                                    @PathVariable(value = "pId",required = false) Long pId ) throws IOException {

        FileResume fileResume = fileResumeService.fetchFileResumeByfId(fFileID);
        System.out.println("文件uuid："+fFileID);
        httpServletResponse.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileResume.getFFileName(),"UTF-8"));
        FileInputStream fis=new FileInputStream(fileResume.getFFileStorePath());
        int len=0;
        byte[] buffer=new byte[1024];
        ServletOutputStream os = httpServletResponse.getOutputStream();
//        httpServletResponse.reset();
        //加了这个不会报错，但是不能下载了只能预览
        while ((len=fis.read(buffer))>0){
            os.write(buffer,0,len);
        }

        fis.close();
        os.close();

        Map<String,Object> sIdAndFpId = new HashMap<String,Object>();
        if (pId==null){
            pId=null;
        }
        sIdAndFpId.put("sId",sId);
        sIdAndFpId.put("fFilePid",pId);
        Map<String, Object> studentInfo = studentService.fetchStuWithResumeById(sIdAndFpId);
        model.addAttribute("stuInfo",studentInfo);
        return "studentSelfInfo";
    }


    @RequestMapping("/downloadCompanyClassicImg/{cId}")
    public String uploadCompanyClassicImg(MultipartFile imgFile,
                                          @PathVariable(value = "cId")Long cId,
                                          Model model) throws IOException {

        System.out.println("我进来了");
        FileCompany repeatedCompanyFile = fileCompanyService.isRepeatedCompanyFile(cId);
        Company storeCompany = companyService.fetchCompanyByCid(cId);
        if (repeatedCompanyFile!=null){
            FileCompany updFileCompany = new FileCompany();
            File companyImgStorePath = new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+storeCompany.getCName()+File.separator);
            File oldFile=new File(repeatedCompanyFile.getFFileStorePath());
            System.out.println( File.separator + repeatedCompanyFile.getFFileStorePath() + File.separator);
            System.out.println(oldFile.getAbsolutePath());
            if(oldFile.delete()){
                System.out.println("删除成功");
            }else {
                System.out.println("删除失败");
            }
            if (!companyImgStorePath.exists()){
                companyImgStorePath.mkdirs();
            }
            imgFile.transferTo(new File(companyImgStorePath.getAbsoluteFile()+File.separator+imgFile.getOriginalFilename()));

            updFileCompany.setFFileName(imgFile.getOriginalFilename());
            updFileCompany.setFFileStorePath(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+storeCompany.getCName()+File.separator+imgFile.getOriginalFilename());
            updFileCompany.setFFileCid(cId);
            updFileCompany.setFFileID(fileCompanyService.isRepeatedCompanyFile(cId).getFFileID());
            fileCompanyService.updateFileCompany(updFileCompany);
            model.addAttribute("ciMsg","更新上传图片成功");
            System.out.println("更新上传图片成功");
            return "bz";
        }




        File companyImgStorePath = new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+storeCompany.getCName()+File.separator);
        System.out.println(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+storeCompany.getCName()+File.separator);
        System.out.println(companyImgStorePath.getAbsoluteFile());

        System.out.println(imgFile.getContentType());
        System.out.println(imgFile.getOriginalFilename());
        if (!companyImgStorePath.exists()){
            companyImgStorePath.mkdirs();
        }
        imgFile.transferTo(new File(companyImgStorePath.getAbsoluteFile()+File.separator+imgFile.getOriginalFilename()));
        FileCompany fileCompany = new FileCompany();
        fileCompany.setFFileID(STRING_NULL);
        fileCompany.setFFileName(imgFile.getOriginalFilename());
        fileCompany.setFFileStorePath(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+storeCompany.getCName()+File.separator+imgFile.getOriginalFilename());
        System.out.println(COMPANY_FILE_STORE_PATH_PREFIX + File.separator + storeCompany.getCName() + File.separator + imgFile.getOriginalFilename());
        fileCompany.setFFileCid(cId);
        fileCompanyService.addFileCompany(fileCompany);
        model.addAttribute("ciMsg","上传成功");
        return "bz";
    }

    @RequestMapping("/fetchCompaniesList/{pageNum}")
    public String fetchCompaniesList(
            @RequestParam(required = false) String pageComSearchInfo,
            Model model,
            HttpSession session,
            @PathVariable(value = "pageNum",required = false) Integer pageNum
    ){
        PageHelper.startPage(pageNum,3);
        String curComSearchInfo = session.getAttribute("curComSearchInfo").toString();
        if (pageComSearchInfo!=null){
            curComSearchInfo=pageComSearchInfo;
            session.setAttribute("curComSearchInfo",pageComSearchInfo);
        }
        Map<String,Object> company=new HashMap<String,Object>();
        company.put("comSearchInfo",curComSearchInfo);
        List<Company> allCompaniesList = companyService.fetchCompaniesList(company);
        PageInfo<Company> allCompaniesListPageInfo=new  PageInfo<Company>(allCompaniesList);
        model.addAttribute("allCompaniesList",allCompaniesList);
        model.addAttribute("allCompaniesListPageInfo",allCompaniesListPageInfo);
        return "allCompaniesList";
    }

    @RequestMapping("/deleteCom/{pageNum}")
    public String deleteCom(@RequestParam(required = false) String pageComSearchInfo,
                            Model model,
                            HttpSession session,
                            @RequestParam(required = false)Long pageCid,
                            @PathVariable(value = "pageNum",required = false) Integer pageNum) throws IOException {

        String cName = companyService.fetchCompanyByCid(pageCid).getCName();


        System.out.println(COMPANY_FILE_STORE_PATH_PREFIX+ File.separator+cName+ File.separator);
        FileUtils.deleteDirectory(new File(COMPANY_FILE_STORE_PATH_PREFIX+ File.separator+cName+ File.separator));


        PartTimeJob partTimeJob1=new PartTimeJob();
        partTimeJob1.setCId(pageCid);
        List<PartTimeJob> allPartTimeJobs = partTimeJobService.fetchAllPartTimeJobs(partTimeJob1);
        for (PartTimeJob pj :
                allPartTimeJobs) {
            Map<String, Object> fileResume=new HashMap<String, Object>();
            fileResume.put("fFilePid",pj.getPId());
            fileResume.put("fFileFlag",Integer.valueOf(DELETED_FLAG).toString());
            fileResumeService.updateFileResume(fileResume);
        }


        Map<String,Object> updCompany=new HashMap<String,Object>();
        updCompany.put("cId",pageCid);
        updCompany.put("cFlag",DELETED_FLAG);
        companyService.updateCompany(updCompany);



        LocalInformation localInformation = new LocalInformation();
        localInformation.setLCid(pageCid);
        localInformation.setLSpcFlag(DELETED_FLAG);
        localInformationService.updateLocalInformation(localInformation);


        ServiceProvide serviceProvide = new ServiceProvide();
        serviceProvide.setSpCid(pageCid);
        serviceProvide.setSpSpcFlag(DELETED_FLAG);
        serviceProvideService.updateServiceProvide(serviceProvide);

        Map<String, Object> comApplyServiceProvideDealMap = new HashMap<String, Object>();
        comApplyServiceProvideDealMap.put("aspCid",pageCid);
        comApplyServiceProvideDealMap.put("aspStatus",DEPRECATED_STATUS);
        applyServiceProvideService.comDealApplyServiceProvide(comApplyServiceProvideDealMap);


        Map<String, Object> comApplyLocalInformationDealMap = new HashMap<String, Object>();
        comApplyLocalInformationDealMap.put("aliCid",pageCid);
        comApplyLocalInformationDealMap.put("aliStatus",DEPRECATED_STATUS);
        applyLocalInformationService.comDealApplyLocalInformation(comApplyLocalInformationDealMap);

        Map<String, Object> comApplyPartTimeJobDealMap = new HashMap<String, Object>();
        comApplyPartTimeJobDealMap.put("apCid",pageCid);
        comApplyPartTimeJobDealMap.put("apStatus",DEPRECATED_STATUS);
        applyPartTimeJobService.comDealApplyPartTimeJob(comApplyPartTimeJobDealMap);

        FileCompany fileCompany = new FileCompany();
        fileCompany.setFFileCid(pageCid);
        fileCompany.setFFileFlag(Integer.valueOf(DELETED_FLAG).toString());
        fileCompanyService.updateFileCompany(fileCompany);


        PartTimeJob partTimeJob = new PartTimeJob();
        partTimeJob.setCId(pageCid);
        partTimeJob.setSpFlag(DELETED_FLAG);
        partTimeJobService.updatePartTimeJob(partTimeJob);




        PageHelper.startPage(pageNum,3);
        String curComSearchInfo = session.getAttribute("curComSearchInfo").toString();
        if (pageComSearchInfo!=null){
            curComSearchInfo=pageComSearchInfo;
            session.setAttribute("curComSearchInfo",pageComSearchInfo);
        }
        Map<String,Object> company=new HashMap<String,Object>();
        company.put("comSearchInfo",curComSearchInfo);
        List<Company> allCompaniesList = companyService.fetchCompaniesList(company);
        PageInfo<Company> allCompaniesListPageInfo=new  PageInfo<Company>(allCompaniesList);
        model.addAttribute("allCompaniesList",allCompaniesList);
        model.addAttribute("allCompaniesListPageInfo",allCompaniesListPageInfo);
        return "allCompaniesList";
    }


    @RequestMapping("/updateCom/{cId}")
    public String updateCom(@RequestParam Map<String ,Object> pageUpdateCom,
                            @PathVariable(value = "cId") Long cId,
                            Model model){

        switch (util.isLegalInputCompanyMap(pageUpdateCom,cId)) {
            case EMPTY_POJO:{model.addAttribute("updateMsg","请填写完整信息");model.addAttribute("comInfo",companyService.fetchCompanyByCid(cId));return "comUpdate";}
            case ERROR_EMAIL:{model.addAttribute("updateMsg","错误的邮箱");model.addAttribute("comInfo",companyService.fetchCompanyByCid(cId));return "comUpdate";}
            case REPEATED_EMAIL:{model.addAttribute("updateMsg","该邮箱已被注册");model.addAttribute("comInfo",companyService.fetchCompanyByCid(cId));return "comUpdate";}
            case SUCCESS:break;
            default:
        }
        Company exCompany = companyService.fetchCompanyByCid(cId);
//获得未修改前的名字并为后面的找相应的文件夹名字做准备
        System.out.println("公司名"+exCompany.getCName());


        pageUpdateCom.put("cId",cId);
        companyService.updateCompany(pageUpdateCom);



        FileCompany oldFile = fileCompanyService.isRepeatedCompanyFile(cId);
//        System.out.println(oldFile.getFFileStorePath());

        if (oldFile!=null){
            //找到了要修改所在的的公司文件表的信息
            FileCompany updFileCompany = new FileCompany();
            updFileCompany.setFFileCid(cId);
            updFileCompany.setFFileStorePath(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+(pageUpdateCom.get("cName").toString())+File.separator+(oldFile.getFFileStorePath().substring(oldFile.getFFileStorePath().lastIndexOf(File.separator)+1,oldFile.getFFileStorePath().length())));
            fileCompanyService.updateFileCompany(updFileCompany);
            System.out.println("前端传来的cname:"+pageUpdateCom.get("cName").toString());
            new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+exCompany.getCName()+File.separator).renameTo(new File(COMPANY_FILE_STORE_PATH_PREFIX+File.separator+(pageUpdateCom.get("cName").toString())+File.separator));

        }

        model.addAttribute("comInfo",companyService.fetchCompanyByCid(cId));
        model.addAttribute("updateMsg","编辑成功");
        return "comUpdate";
    }



    @RequestMapping("/addCom")
    public String addCom(@RequestParam Map<String ,Object> pageRegisterCom,
                         Model model) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        switch (util.isLegalInputCompanyMap(pageRegisterCom,LONG_NULL)) {
            case EMPTY_POJO:{ model.addAttribute("registerMsg","请填写完整信息");return "registerCom"; }
            case INCONSISTENT_PASSWORD:{model.addAttribute("registerMsg","密码输入不一致");return "registerCom";}
            case ERROR_EMAIL:{model.addAttribute("registerMsg","错误的邮箱");return "registerCom";}
            case REPEATED_EMAIL:{model.addAttribute("registerMsg","该邮箱已被注册");return "registerCom";}
            case SUCCESS:break;
            default:
        }

        if (pageRegisterCom.get("checkProtocol")==null){
            model.addAttribute("registerMsg","你没有勾选协议");
            return "registerCom";
        }
        pageRegisterCom.put("cId",INTEGER_NULL);
        pageRegisterCom.put("cRegisterTime",util.CSTDateFormatFromPageTransferToString(sdf.parse(sdf.format(System.currentTimeMillis())),sdf));
        pageRegisterCom.put("cPassword",pageRegisterCom.get("cPassword2"));

        companyService.addCompany(pageRegisterCom);
        util.sendMail(ID_SUBJECT,ID_TEXT+pageRegisterCom.get("cId").toString(),DEFAULT_MAIL_SENDER,companyService.fetchCompanyByCid(Long.valueOf(pageRegisterCom.get("cId").toString())).getCEmail());

        model.addAttribute("registerMsg","注册成功，点击左侧按钮或者上方按钮前往登录");
        return "registerCom";
    }

    @RequestMapping("/comFetchPasswordByEmail")
    public String comFetchPasswordByEmail(@RequestParam String usrIdOrNameOrEmailOrPhone,
                                          Model model){
        if (usrIdOrNameOrEmailOrPhone==null || "".equals(usrIdOrNameOrEmailOrPhone)){
            model.addAttribute("fpMsg",INTEGER_NULL);
            return "comForgetPassword";
        }
        Company existCompany = companyService.fetchExistCompany(usrIdOrNameOrEmailOrPhone);
        if (existCompany==null){
            model.addAttribute("fpMsg",INTEGER_NULL);
            return "comForgetPassword";
        }
        if (existCompany.getCEmail()==null){
            model.addAttribute("fpMsg",INTEGER_NULL);
            return "comForgetPassword";
        }

        util.sendMail(PASSWORD_SUBJECT,PASSWORD_TEXT+existCompany.getCPassword(),DEFAULT_MAIL_SENDER,existCompany.getCEmail());
        model.addAttribute("fpMsg",INTEGER_NULL+1);
        return "comForgetPassword";
    }


    @RequestMapping("/comRevisePassword/{cId}")
    public String comRevisePassword(@RequestParam Map<String ,Object> pageUpdateCom,
                                    @PathVariable(value = "cId") Long cId,
                                    Model model){
        Map<String,Object> updateCompany=new HashMap<String,Object>();
        switch (util.isLegalInputCompanyMap(pageUpdateCom,cId)) {
            case EMPTY_POJO:{ model.addAttribute("updateMsg","请填写完整信息");model.addAttribute("oldPassword",companyService.fetchCompanyByCid(cId).getCPassword());return "comRevisePassword"; }
            case INCONSISTENT_PASSWORD:{model.addAttribute("updateMsg","密码输入不一致");model.addAttribute("oldPassword",companyService.fetchCompanyByCid(cId).getCPassword());return "comRevisePassword";}
            case SUCCESS:break;
            default:
        }
        updateCompany.put("cId",cId);
        updateCompany.put("cPassword",pageUpdateCom.get("cPassword2").toString());
        companyService.updateCompany(updateCompany);

        model.addAttribute("updateMsg","更新成功");
        model.addAttribute("oldPassword",companyService.fetchCompanyByCid(cId).getCPassword());

        return "comRevisePassword";
    }

    @RequestMapping("/test")
    @ResponseBody
//    @CrossOrigin
//    在preHandle里设置了，这里就可以不用crossOrigin了
    public String testImg(HttpServletResponse response){
        String res="你好 我是idea发送的ajax，区别于vscode";
        System.out.println("我进来了idea的ajax跨域实验");
        return res;
    }
}
