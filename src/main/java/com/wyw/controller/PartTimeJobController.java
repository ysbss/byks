package com.wyw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyw.pojo.ApplyPartTimeJob;
import com.wyw.pojo.FileResume;
import com.wyw.pojo.PartTimeJob;
import com.wyw.pojo.Student;
import com.wyw.service.ApplyPartTimeJobService;
import com.wyw.service.FileResumeService;
import com.wyw.service.PartTimeJobService;
import com.wyw.service.StudentService;
import com.wyw.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wyw.utils.FinalStaticValue.*;

/**
 * @author 鱼酥不是叔
 */
@Controller
@RequestMapping("/PartTimeJob")
public class PartTimeJobController {

    @Autowired
    @Qualifier("PartTimeJobServiceImpl")
    PartTimeJobService partTimeJobService;

    @Autowired
    @Qualifier("ApplyPartTimeJobServiceImpl")
    ApplyPartTimeJobService applyPartTimeJobService;

    @Autowired
    @Qualifier("FileResumeServiceImpl")
    FileResumeService fileResumeService;

    @Resource
    StudentService studentService;




//    @RequestMapping("/fetchAllSpecialPartTimeJob")
//    public String fetchAllSpecialPartTimeJob(Model model){
//        List<Map<String, Object>> allSpecialPartTimeJobs = partTimeJobService.getAllSpecialPartTimeJobs();
//        model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJob);
//        return "commons/common";
//        return "";
//    }

    @RequestMapping("/fetchSpcPartTimeJob/{pId}")
    public String fetchSpcPartTimeJob(@PathVariable(value = "pId",required = false) Long pId,
                                      Model model,
                                      HttpSession session) throws ParseException {
        Util util = new Util();
        System.out.println(pId);
        Map<String, Object> partTimeJob = partTimeJobService.fetchSpcPartTimeJobByPid(pId);
        PartTimeJob updatePartTimeJob = new PartTimeJob();
        updatePartTimeJob.setPId(Long.valueOf(partTimeJob.get("pId").toString()));
        updatePartTimeJob.setPBrowseNum(Integer.valueOf(partTimeJob.get("pBrowseNum").toString())+1);
        partTimeJobService.updatePartTimeJob(updatePartTimeJob);
        partTimeJob = partTimeJobService.fetchSpcPartTimeJobByPid(pId);
        List<Map<String, Object>> approximatePartTimeJobs = partTimeJobService.fetchApproximatePartTimeJobByPid(pId);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        partTimeJob.put("pSubmitTime",util.computePageDays(partTimeJob.get("pSubmitTime").toString(),sdf));
        model.addAttribute("partTimeJob",partTimeJob);
        //还要写一个方法将类似的展示出来
        model.addAttribute("approximatePartTimeJobs",approximatePartTimeJobs);
        return "zzy";
    }


//    @RequestMapping(value = {"/fetchAllPartTimeJob/{pageNum}/{partTimeJobSearchInfo}","/fetchAllPartTimeJob/{pageNum}"})
    @RequestMapping(value = {"/fetchAllPartTimeJob/{pageNum}"})
    public String fetchAllPartTimeJob(@RequestParam(required = false) String pagePartTimeJobSearchInfo,
                                      Model model,
                                      HttpSession session,
//                                      @RequestParam(value="pageNum",required = false,defaultValue = "1")
                                          @PathVariable(value = "pageNum",required = false) Integer pageNum){


        System.out.println("pageNUm:"+pageNum);
        System.out.println("partTimeJobSearchInfo:"+pagePartTimeJobSearchInfo);
        PageHelper.startPage(pageNum,5);
        System.out.println(session.getAttribute("partTimeJobSearchInfo").toString());

        String curPartTimeJobSearchInfo = session.getAttribute("partTimeJobSearchInfo").toString();

        System.out.println("curPartTimeJobSearchInfo:"+curPartTimeJobSearchInfo);
        List<Map<String, Object>> allPartTimeJobs=new ArrayList<Map<String, Object>>();

        if(pagePartTimeJobSearchInfo!=null){
            curPartTimeJobSearchInfo=pagePartTimeJobSearchInfo;
            session.setAttribute("partTimeJobSearchInfo",pagePartTimeJobSearchInfo);
        }
        System.out.println("curPartTimeJobSearchInfo:"+curPartTimeJobSearchInfo);
        allPartTimeJobs = partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo);

        PageInfo<Map<String, Object>> allPartTimeJobsPageInfo = new PageInfo<Map<String, Object>>(allPartTimeJobs);
        model.addAttribute("allPartTimeJobs",allPartTimeJobs);
        model.addAttribute("allPartTimeJobsPageInfo",allPartTimeJobsPageInfo);

//        if ((partTimeJobSearchInfo==""||partTimeJobSearchInfo==null)&&
//                (curPartTimeJobSearchInfo!=null)){
//
//            for (Map m:allPartTimeJobs
//                 ) {
//                System.out.println(m.get("pId"));
//            }
//        }else {
//
//            for (Map m:allPartTimeJobs
//            ) {
//                System.out.println(m.get("pId"));
//            }
//        }



        return "bft_more";
//        return "redirect:/PartTimeJob/fetchAllPartTimeJob/"+curPartTimeJobSearchInfo+"/"+pageNum;
    }

    @ResponseBody
    @RequestMapping("/uploadResumeFile")
    public String uploadResumeFile(MultipartFile resumeFile){

        return "";
    }


    @RequestMapping("/applyPartTimeJob/{pId}/{cId}")
    public String applyPartTimeJob(MultipartFile resumeFile,
                                   Model model,
                                   @PathVariable(value = "pId",required = false) Long pId,
                                   @PathVariable(value = "cId",required = false) Long cId,
                                   HttpSession session) throws IOException, ParseException {
        /**
         * 1.将简历文件存入文件库，同时上传到指定路径(文件路径应该是当前路径下/stuResume/学生名)
         * 2.生成申请表的相关信息（要获取岗位id（在url链接上），学生id（在session里），公司id（通过岗位获取），申请时间现在生成）
         *3.生成文件表相应信息
         * */
/**
 * 判断这个学生是否已经申请过该职位
 * */
        Map<String, Object> detectRepeatMap = new HashMap<String, Object>();
        detectRepeatMap.put("apPid",pId);
        detectRepeatMap.put("apSid",Long.valueOf(session.getAttribute("currentStuId").toString()));
        detectRepeatMap.put("apCid",cId);
        ApplyPartTimeJob repeatedInfoInApplyPartTimeJob = applyPartTimeJobService.isRepeatedInfoInApplyPartTimeJob(detectRepeatMap);
        Map<String, Object> partTimeJob = partTimeJobService.fetchSpcPartTimeJobByPid(pId);
        PartTimeJob updatePartTimeJob = new PartTimeJob();
        updatePartTimeJob.setPId(Long.valueOf(partTimeJob.get("pId").toString()));
        updatePartTimeJob.setPAppointmentNum(Integer.valueOf(partTimeJob.get("pAppointmentNum").toString())+1);


        model.addAttribute("partTimeJob",partTimeJob);
        if(repeatedInfoInApplyPartTimeJob!=null){
            System.out.println("i came in repeated situation");
            model.addAttribute("apMsg","请勿重复申请同一个职位");
            List<Map<String, Object>> approximatePartTimeJobs = partTimeJobService.fetchApproximatePartTimeJobByPid(pId);
            model.addAttribute("approximatePartTimeJobs",approximatePartTimeJobs);
            System.out.println(model.getAttribute("apMsg"));
            return "zzy";
//            return "redirect:/PartTimeJob/fetchSpcPartTimeJob/"+pId;
        }

        Util util = new Util();
        Student curStudent = studentService.fetchStuById(Long.valueOf(session.getAttribute("currentStuId").toString()));

        File resumeStoreFilePath = new File(RESUME_FILE_STORE_PATH_PREFIX+File.separator+curStudent.getSName()+File.separator);
        System.out.println(RESUME_FILE_STORE_PATH_PREFIX+File.separator+curStudent.getSName()+File.separator);
        System.out.println(resumeStoreFilePath.getAbsoluteFile());
        System.out.println(resumeFile.getContentType());
        System.out.println(resumeFile.getOriginalFilename());


        if (!resumeStoreFilePath.exists()){
            resumeStoreFilePath.mkdirs();
            System.out.println("make directory success");
        }
        resumeFile.transferTo(new File(resumeStoreFilePath.getAbsoluteFile()+File.separator+resumeFile.getOriginalFilename()));
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        ApplyPartTimeJob applyPartTimeJob = new ApplyPartTimeJob();
        applyPartTimeJob.setApPid(pId);
        applyPartTimeJob.setApSid(Long.valueOf(session.getAttribute("currentStuId").toString()));
        applyPartTimeJob.setApCid(cId);
//        applyPartTimeJob.setApSubmitTime(sdf.format(System.currentTimeMillis()));
        applyPartTimeJob.setApStatus(UN_DISPOSED_STATUS);
        applyPartTimeJob.setApSubmitTime(util.CSTDateFormatFromPageTransferToString(sdf.parse(sdf.format(System.currentTimeMillis())),sdf));

        System.out.println(sdf.format(System.currentTimeMillis()));
        applyPartTimeJobService.addApplyPartTimeJob(applyPartTimeJob);
        //添加到了apply表
        FileResume fileResume = new FileResume();
        fileResume.setFFileID(STRING_NULL);
        fileResume.setFFileName(resumeFile.getOriginalFilename());
        fileResume.setFFileStorePath(RESUME_FILE_STORE_PATH_PREFIX+File.separator+curStudent.getSName()+File.separator+resumeFile.getOriginalFilename());
        System.out.println(RESUME_FILE_STORE_PATH_PREFIX+File.separator+curStudent.getSName()+File.separator+resumeFile.getOriginalFilename());
        fileResume.setFFileSid(Long.valueOf(session.getAttribute("currentStuId").toString()));
        fileResume.setFFilePid(pId);
        fileResumeService.addFileResume(fileResume);
        //添加到了文件表


        partTimeJob.put("pSubmitTime",util.computePageDays(partTimeJob.get("pSubmitTime").toString(),sdf));
        partTimeJobService.updatePartTimeJob(updatePartTimeJob);
        List<Map<String, Object>> approximatePartTimeJobs = partTimeJobService.fetchApproximatePartTimeJobByPid(pId);
        model.addAttribute("approximatePartTimeJobs",approximatePartTimeJobs);
        model.addAttribute("apMsg","申请成功");

//        return "redirect:/PartTimeJob/fetchSpcPartTimeJob/"+pId;
        return "zzy";
    }


    @RequestMapping("/fetchAllPartTimeJobsByCidAndSearchInfo/{cId}/{pageNum}")
    public String fetchAllPartTimeJobsByCidAndSearchInfo(@RequestParam(required = false) String pageAddPartTimeJobComSearchInfo,
                                                         Model model,
                                                         HttpSession session,
                                                         @PathVariable(value = "cId")Long cId,
                                                         @PathVariable(value = "pageNum",required = false) Integer pageNum){


        PageHelper.startPage(pageNum,3);
        if (session.getAttribute("curAddPartTimeJobComSearchInfo")==null){
            session.setAttribute("curAddPartTimeJobComSearchInfo",STRING_NULL);
        }
        String curAddPartTimeJobComSearchInfo = session.getAttribute("curAddPartTimeJobComSearchInfo").toString();
        List<Map<String, Object>> allPartTimeJobsByCidAndSearchInfo=new ArrayList<Map<String, Object>>();
        Map<String, Object> cIdAndSearchInfo = new HashMap<String, Object>();
        cIdAndSearchInfo.put("cId",cId);
        if (pageAddPartTimeJobComSearchInfo!=null){
            curAddPartTimeJobComSearchInfo=pageAddPartTimeJobComSearchInfo;
            session.setAttribute("curAddPartTimeJobComSearchInfo",pageAddPartTimeJobComSearchInfo);
        }
        cIdAndSearchInfo.put("partTimeJobSearchInfo",curAddPartTimeJobComSearchInfo);
         allPartTimeJobsByCidAndSearchInfo = partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo);
        PageInfo<Map<String, Object>> allPartTimeJobsByCidAndSearchInfoPageInfo = new PageInfo<Map<String, Object>>(allPartTimeJobsByCidAndSearchInfo);
        model.addAttribute("allPartTimeJobsByCidAndSearchInfo",allPartTimeJobsByCidAndSearchInfo);
        model.addAttribute("allPartTimeJobsByCidAndSearchInfoPageInfo",allPartTimeJobsByCidAndSearchInfoPageInfo);


        return "comAddPartTimeJobList";
    }


    @RequestMapping("/deletePartTimeJobByPid/{cId}/{pageNum}")
    public String deletePartTimeJobByPid(@RequestParam(required = false) String pageAddPartTimeJobComSearchInfo,
                                           Model model,
                                           HttpSession session,
                                           @RequestParam(required = false)Long pagePid,
                                           @PathVariable(value = "cId")Long cId,
                                           @PathVariable(value = "pageNum",required = false) Integer pageNum){

        PartTimeJob updatePartTimeJob = new PartTimeJob();
        updatePartTimeJob.setSpFlag(DELETED_FLAG);
        updatePartTimeJob.setPId(pagePid);
        partTimeJobService.updatePartTimeJob(updatePartTimeJob);
        Map<String,Object> comDealMap = new HashMap<String,Object>();
        comDealMap.put("apPid",pagePid);
        comDealMap.put("apStatus",DEPRECATED_STATUS);
        applyPartTimeJobService.comDealApplyPartTimeJob(comDealMap);


        PageHelper.startPage(pageNum,3);
        String curAddPartTimeJobComSearchInfo = session.getAttribute("curAddPartTimeJobComSearchInfo").toString();
        List<Map<String, Object>> allPartTimeJobsByCidAndSearchInfo=new ArrayList<Map<String, Object>>();
        HashMap<String, Object> cIdAndSearchInfo = new HashMap<String, Object>();
        cIdAndSearchInfo.put("cId",cId);
        if (pageAddPartTimeJobComSearchInfo!=null){
            curAddPartTimeJobComSearchInfo=pageAddPartTimeJobComSearchInfo;
            session.setAttribute("curAddPartTimeJobComSearchInfo",pageAddPartTimeJobComSearchInfo);
        }
        cIdAndSearchInfo.put("partTimeJobSearchInfo",curAddPartTimeJobComSearchInfo);
        allPartTimeJobsByCidAndSearchInfo = partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo);
        PageInfo<Map<String, Object>> allPartTimeJobsByCidAndSearchInfoPageInfo = new PageInfo<Map<String, Object>>(allPartTimeJobsByCidAndSearchInfo);
        model.addAttribute("allPartTimeJobsByCidAndSearchInfo",allPartTimeJobsByCidAndSearchInfo);
        model.addAttribute("allPartTimeJobsByCidAndSearchInfoPageInfo",allPartTimeJobsByCidAndSearchInfoPageInfo);
        return "comAddPartTimeJobList";
    }

    @RequestMapping("/addPartTimeJob/{cId}")
    public String addPartTimeJob(@RequestParam Map<String,Object> pagePartTimeJob,
                                  @PathVariable(value = "cId")Long cId,
                                  HttpSession session,
                                  Model model) throws ParseException {
        Util util = new Util();
        System.out.println(pagePartTimeJob.size());
        int flag=util.isLegalInputPartTimeJobMap(pagePartTimeJob);
        switch (flag){
            case EMPTY_POJO:{model.addAttribute("addMsg","请输入所有信息");return "comAddPartTimeJob";}
            case ILLEGAL_INPUT_SALARY_NUM:{model.addAttribute("addMsg","薪水输入错误");return "comAddPartTimeJob";}
            case ILLEGAL_INPUT_EXPERIENCE_NUM:{model.addAttribute("addMsg","工作经验输入错误");return "comAddPartTimeJob";}
            case ILLEGAL_INPUT_AGE_NUM:{model.addAttribute("addMsg","年龄输入错误");return "comAddPartTimeJob";}
            case SUCCESS:break;
            default:
        }
        /**
         * 判断输入值是否合法（比如两个数字输入框 后面的要比前面的大）
         * */
        PartTimeJob addPartTimeJob = util.getPartTimeJobByPageParam(pagePartTimeJob);

        addPartTimeJob.setCId(cId);
        partTimeJobService.addPartTimeJob(addPartTimeJob);

        PageHelper.startPage(1,3);
        Map<String, Object> cIdAndSearchInfo = new HashMap<String, Object>();
        cIdAndSearchInfo.put("cId",cId);
        if (session.getAttribute("curAddPartTimeJobComSearchInfo")==null){
            session.setAttribute("curAddPartTimeJobComSearchInfo",STRING_NULL);
        }
        cIdAndSearchInfo.put("partTimeJobSearchInfo",session.getAttribute("curAddPartTimeJobComSearchInfo").toString());
        List<Map<String, Object>> allPartTimeJobsByCidAndSearchInfo = partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo);
        PageInfo<Map<String, Object>> allPartTimeJobsByCidAndSearchInfoPageInfo = new PageInfo<Map<String, Object>>(allPartTimeJobsByCidAndSearchInfo);
        model.addAttribute("allPartTimeJobsByCidAndSearchInfo",allPartTimeJobsByCidAndSearchInfo);
        model.addAttribute("allPartTimeJobsByCidAndSearchInfoPageInfo",allPartTimeJobsByCidAndSearchInfoPageInfo);

        return "comAddPartTimeJobList";
    }

    @RequestMapping("/updatePartTimeJob/{pId}")
    public String updatePartTimeJob(@RequestParam Map<String,Object> pagePartTimeJob,
                                 @PathVariable(value = "pId")Long pId,
                                 Model model) throws ParseException {
        Util util = new Util();
        System.out.println(pagePartTimeJob.size());
        int flag=util.isLegalInputPartTimeJobMap(pagePartTimeJob);
        switch (flag){
            case EMPTY_POJO:{model.addAttribute("updateMsg","请输入所有信息");Map<String, Object> pagePartTimeJob1 = partTimeJobService.fetchSpcPartTimeJobByPid(pId);pagePartTimeJob1=util.getPagePartTimeJobFromMap(pagePartTimeJob1);model.addAttribute("partTimeJob",pagePartTimeJob1);return "comUpdatePartTimeJob";}
            case ILLEGAL_INPUT_SALARY_NUM:{model.addAttribute("updateMsg","薪水输入错误");Map<String, Object> pagePartTimeJob1 = partTimeJobService.fetchSpcPartTimeJobByPid(pId);pagePartTimeJob1=util.getPagePartTimeJobFromMap(pagePartTimeJob1);model.addAttribute("partTimeJob",pagePartTimeJob1);return "comUpdatePartTimeJob";}
            case ILLEGAL_INPUT_EXPERIENCE_NUM:{model.addAttribute("updateMsg","工作经验输入错误");Map<String, Object> pagePartTimeJob1 = partTimeJobService.fetchSpcPartTimeJobByPid(pId);pagePartTimeJob1=util.getPagePartTimeJobFromMap(pagePartTimeJob1);model.addAttribute("partTimeJob",pagePartTimeJob1);return "comUpdatePartTimeJob";}
            case ILLEGAL_INPUT_AGE_NUM:{model.addAttribute("updateMsg","年龄输入错误");Map<String, Object> pagePartTimeJob1 = partTimeJobService.fetchSpcPartTimeJobByPid(pId);pagePartTimeJob1=util.getPagePartTimeJobFromMap(pagePartTimeJob1);model.addAttribute("partTimeJob",pagePartTimeJob1);return "comUpdatePartTimeJob";}
            case SUCCESS:break;
            default:
        }
        pagePartTimeJob.put("pId",pId);
        PartTimeJob updatePartTimeJob =util.getPartTimeJobByPageParam(pagePartTimeJob);
        System.out.println("要更新的pid："+updatePartTimeJob.getPId());
        partTimeJobService.updatePartTimeJob(updatePartTimeJob);




        Map<String, Object> PartTimeJob = partTimeJobService.fetchSpcPartTimeJobByPid(pId);
        PartTimeJob=util.getPagePartTimeJobFromMap(PartTimeJob);
        model.addAttribute("partTimeJob",PartTimeJob);
        model.addAttribute("updateMsg","更新成功");
        return "comUpdatePartTimeJob";
    }

}
