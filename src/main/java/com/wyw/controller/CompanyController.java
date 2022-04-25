package com.wyw.controller;


import com.wyw.pojo.Company;
import com.wyw.pojo.FileCompany;
import com.wyw.pojo.FileResume;
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

import static com.wyw.utils.FinalStaticValue.COMPANY_FILE_STORE_PATH_PREFIX;
import static com.wyw.utils.FinalStaticValue.STRING_NULL;

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



    @RequestMapping("/companyLogin")
    public String companyLogin(@RequestParam(value = "cName",required = false) String cName,
                               @RequestParam(value = "cPassword",required = false) String cPassword,
                               HttpSession session,
                               Model model) throws ParseException {
        Company company = companyService.fetchCompanyByName(cName);
        Util util = new Util();
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
    public String downloadCompanyClassicImg(MultipartFile imgFile,
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






}
