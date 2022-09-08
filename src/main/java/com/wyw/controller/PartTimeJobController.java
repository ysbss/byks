package com.wyw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.Page;
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
import com.wyw.utils.RedisUtils;
import com.wyw.utils.Util;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
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
import java.util.concurrent.atomic.AtomicInteger;

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

    @Resource
    RedisUtils redisUtils;




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
//        System.out.println(partTimeJob);
        PartTimeJob updatePartTimeJob = new PartTimeJob();
        updatePartTimeJob.setPId(Long.valueOf(partTimeJob.get("pId").toString()));
        updatePartTimeJob.setPBrowseNum(Integer.parseInt(partTimeJob.get("pBrowseNum").toString())+1);
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
        List<Map<String, Object>> allPartTimeJobs=new ArrayList<Map<String, Object>>();
        String curPartTimeJobSearchInfo = session.getAttribute("partTimeJobSearchInfo").toString();
        System.out.println(session.getAttribute("partTimeJobSearchInfo").toString());
        if(pagePartTimeJobSearchInfo!=null){
            curPartTimeJobSearchInfo=pagePartTimeJobSearchInfo;
            session.setAttribute("partTimeJobSearchInfo",pagePartTimeJobSearchInfo);
        }
        System.out.println("curPartTimeJobSearchInfo:"+curPartTimeJobSearchInfo);
//        PageHelper.startPage(pageNum,PAGE_SIZE_FIVE);
        Page<Map<String,Object>> page = new Page<Map<String,Object>>();

        page.setPageSize(PAGE_SIZE_FIVE);
        if (redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, 0, -1).size()!=partTimeJobService.getAllPartTimeJobs(pagePartTimeJobSearchInfo).size()){
//            page.addAll(partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo));//将这里的page丢到最后的pageInfo里面
            page.setTotal(partTimeJobService.getAllPartTimeJobs(pagePartTimeJobSearchInfo).size());
        }else {
//            page.addAll(JSON.parseObject(JSON.toJSONString(redisUtils.zRangeByScore("AllPartTimeJobs" + curPartTimeJobSearchInfo, Integer.valueOf((pageNum - 1) * PAGE_SIZE_FIVE).doubleValue(), Integer.valueOf((PAGE_SIZE_FIVE *pageNum)-1).doubleValue())), new TypeReference<List<Map<String, Object>>>() {
//            }.getType()));//将这里的page丢到最后的pageInfo里面
            page.setTotal(redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, 0,  -1).size());
        }
        if (pageNum>(page.getTotal()/PAGE_SIZE_FIVE)){
            page.setPageNum(Double.valueOf(Math.ceil( (page.getTotal() / PAGE_SIZE_FIVE.doubleValue()))).intValue());
            pageNum=Double.valueOf(Math.ceil( (page.getTotal() / PAGE_SIZE_FIVE.doubleValue()))).intValue();
            System.out.println("pageNum:"+pageNum);
        }else {
            page.setPageNum(pageNum);
        }

//        System.out.println("**************");
//        System.out.println(partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo));
//        System.out.println("**************");

//        if (redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, 0, -1).size()!=partTimeJobService.getAllPartTimeJobs(pagePartTimeJobSearchInfo).size()){
//            page.addAll(partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo));//将这里的page丢到最后的pageInfo里面
//        }else {
//            System.out.println(redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, 0, -1).size());
//            page.addAll(JSON.parseObject(redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, 0, -1).toString(), new TypeReference<List<Map<String, Object>>>() {
//            }.getType()));//将这里的page丢到最后的pageInfo里面
//
//        }


//        System.out.println("curPartTimeJobSearchInfo:"+curPartTimeJobSearchInfo);
//        allPartTimeJobs= partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo);
//        allPartTimeJobs.forEach(System.out::println);
//        System.out.println((long) (pageNum - 1) * PAGE_SIZE_FIVE);
//        System.out.println((((long) PAGE_SIZE_FIVE *pageNum)-1));
        if (redisUtils.zRangeByScore("AllPartTimeJobs"+curPartTimeJobSearchInfo,Integer.valueOf((pageNum - 1) * PAGE_SIZE_FIVE).doubleValue(), Integer.valueOf((PAGE_SIZE_FIVE *pageNum)-1).doubleValue())==null
    ||redisUtils.zRangeByScore("AllPartTimeJobs"+curPartTimeJobSearchInfo,Integer.valueOf((pageNum - 1) * PAGE_SIZE_FIVE).doubleValue(), Integer.valueOf((PAGE_SIZE_FIVE *pageNum)-1).doubleValue()).isEmpty()
        ){
//            System.out.println(partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo).toString());
//            System.out.println(JSON.toJSONString(partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo)));
//            redisUtils.zAdd("AllPartTimeJobs", JSON.toJSONString(partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo)),pageNum.doubleValue());
            //放入的set应该是一条一条的 而不是一整个页面数据放在第一条
            PageHelper.startPage(pageNum,PAGE_SIZE_FIVE);//会开启分页
            //如果没有进入下面这个if语句让if语句的sql分页那么有可能引发其他地方进行分页出现错误。
            allPartTimeJobs = partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo);
            //通过变量让startPage一定可以和sql语句绑定
            int methodPageNum=(pageNum-1)*PAGE_SIZE_FIVE;
            AtomicInteger finalMethodPageNum= new AtomicInteger(methodPageNum);
            System.out.println(finalMethodPageNum);
            String finalCurPartTimeJobSearchInfo = curPartTimeJobSearchInfo;
//            partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo).forEach(apj->{
//                redisUtils.zAdd("AllPartTimeJobs"+ finalCurPartTimeJobSearchInfo, JSON.toJSONString(apj),redisUtils.zRange("AllPartTimeJobs"+finalCurPartTimeJobSearchInfo,0,-1)==null||redisUtils.zRange("AllPartTimeJobs"+finalCurPartTimeJobSearchInfo,0,-1).isEmpty()?0:Double.parseDouble(String.valueOf(redisUtils.zRange("AllPartTimeJobs"+finalCurPartTimeJobSearchInfo,0,-1).size())));
//            });
//            partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo)
                    allPartTimeJobs.forEach(apj->{
//                        redisUtils.zAdd("AllPartTimeJobs"+ finalCurPartTimeJobSearchInfo, JSON.toJSONString(apj),Double.parseDouble(String.valueOf(finalMethodPageNum.get())));
                        //用这个的话 会导致存在redis里的会是有转义字符的json字符串 而不是json对象
                        redisUtils.zAdd("AllPartTimeJobs"+ finalCurPartTimeJobSearchInfo, apj,Double.parseDouble(String.valueOf(finalMethodPageNum.get())));
                finalMethodPageNum.getAndIncrement();
                System.out.println(finalMethodPageNum);
            });
//            redisUtils.zAdd("AllPartTimeJobs", JSON.toJSONString(allPartTimeJobs),pageNum.doubleValue());
            redisUtils.expire("AllPartTimeJobs"+curPartTimeJobSearchInfo,3600);
        }
        System.out.println(JSON.parseArray(JSON.toJSONString(redisUtils.zRangeByScore("AllPartTimeJobs" + curPartTimeJobSearchInfo, Integer.valueOf((pageNum - 1) * PAGE_SIZE_FIVE).doubleValue(), Integer.valueOf((PAGE_SIZE_FIVE *pageNum)-1).doubleValue()))));
        System.out.println(JSON.parseArray(JSON.toJSONString(redisUtils.zRangeByScore("AllPartTimeJobs" + curPartTimeJobSearchInfo, Integer.valueOf((pageNum - 1) * PAGE_SIZE_FIVE).doubleValue(), Integer.valueOf((PAGE_SIZE_FIVE *pageNum)-1).doubleValue()))).getClass());

//        Object o = JSON.parseObject(JSON.toJSONString(redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, (pageNum - 1) * PAGE_SIZE_FIVE.longValue(), (PAGE_SIZE_FIVE.longValue() * pageNum) - 1)), new TypeReference<List<Map<String, Object>>>() {
//        }.getType());
//        Object o = JSON.parseObject(redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, (pageNum - 1) * PAGE_SIZE_FIVE.longValue(), (PAGE_SIZE_FIVE.longValue() * pageNum) - 1).toString(), new TypeReference<List<Map<String, Object>>>() {
//        }.getType());
//        System.out.println(o);
//        System.out.println(o.getClass());
//        allPartTimeJobs=JSON.parseObject(redisUtils.zRangeByScore("AllPartTimeJobs" + curPartTimeJobSearchInfo, Integer.valueOf((pageNum - 1) * PAGE_SIZE_FIVE).doubleValue(), Integer.valueOf((PAGE_SIZE_FIVE *pageNum)-1).doubleValue()).toString(), new TypeReference<List<Map<String, Object>>>() {
//        }.getType());
        allPartTimeJobs=JSON.parseObject(JSON.toJSONString(redisUtils.zRangeByScore("AllPartTimeJobs" + curPartTimeJobSearchInfo, Integer.valueOf((pageNum - 1) * PAGE_SIZE_FIVE).doubleValue(), Integer.valueOf((PAGE_SIZE_FIVE *pageNum)-1).doubleValue())), new TypeReference<List<Map<String, Object>>>() {
        }.getType());
//        allPartTimeJobs.addAll(JSON.parseArray(JSON.toJSONString(redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, (pageNum - 1) * PAGE_SIZE_FIVE.longValue(), (PAGE_SIZE_FIVE.longValue() * pageNum) - 1))));
//        System.out.println(allPartTimeJobs);
//        System.out.println(JSON.toJSONString(redisUtils.zRange("AllPartTimeJobs"+curPartTimeJobSearchInfo,(pageNum-1)*PAGE_SIZE_FIVE.longValue(), (PAGE_SIZE_FIVE.longValue() *pageNum)-1)));
//        allPartTimeJobs = JSON.parseObject(JSON.toJSONString(redisUtils.zRange("AllPartTimeJobs"+curPartTimeJobSearchInfo,(pageNum-1)*PAGE_SIZE_FIVE.longValue(), (PAGE_SIZE_FIVE.longValue() *pageNum)-1)),new TypeReference<List<Map<String,Object>>>(){}.getType());
//        Set<Object> objects = redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, (pageNum - 1) * PAGE_SIZE_FIVE.longValue(), (PAGE_SIZE_FIVE.longValue() * pageNum) - 1);
//        System.out.println(objects);
//        System.out.println(JSON.parseArray(JSON.toJSONString(objects)));
//        for (Object o:
//        JSON.parseArray(JSON.toJSONString(redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, (pageNum - 1) * PAGE_SIZE_FIVE.longValue(), (PAGE_SIZE_FIVE.longValue() * pageNum) - 1)))) {
//            System.out.println("来看OOOOOOOOOO");
//            System.out.println(o);
//            System.out.println(JSON.toJSONString(o));
//            System.out.println(o.toString());
//            System.out.println(JSON.parseArray(o.toString()));
//            for (Object ok:
//            JSON.parseArray(o.toString())) {
//                System.out.println("我终于来了");
//                System.out.println(ok);
//                System.out.println(JSON.parseObject(JSON.toJSONString(ok), new TypeReference<Map<String, Object>>() {
//                }).toString());
//                allPartTimeJobs.add(JSON.parseObject(JSON.toJSONString(ok), new TypeReference<Map<String, Object>>() {
//                }));
//            }
//            System.out.println(JSON.parseObject(o.toString()));
//        }
//        System.out.println(Optional.ofNullable(JSON.parseObject(JSON.toJSONString(partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo)), new TypeReference<List<Map<String, Object>>>() {
//        }.getType())));
//        System.out.println(Optional.ofNullable(JSON.parseObject(JSON.toJSONString(redisUtils.zRange("AllPartTimeJobs" + curPartTimeJobSearchInfo, (pageNum - 1) * PAGE_SIZE_FIVE.longValue(), (PAGE_SIZE_FIVE.longValue() * pageNum) - 1)), new TypeReference<List<Map<String, Object>>>() {
//        }.getType())));
//        allPartTimeJobs=JSON.parseObject(JSON.toJSONString(partTimeJobService.getAllPartTimeJobs(curPartTimeJobSearchInfo)), new TypeReference<List<Map<String, Object>>>() {
//        }.getType());

//        PageInfo<Map<String, Object>> allPartTimeJobsPageInfo = new PageInfo<Map<String, Object>>(allPartTimeJobs);
        PageInfo<Map<String, Object>> allPartTimeJobsPageInfo = new PageInfo<Map<String, Object>>(page);
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
         * 3.生成文件表相应信息
         * */

 //判断这个学生是否已经申请过该职位
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Map<String, Object> detectRepeatMap = new HashMap<String, Object>();
        detectRepeatMap.put("apPid",pId);
        detectRepeatMap.put("apSid",Long.valueOf(session.getAttribute("currentStuId").toString()));
        detectRepeatMap.put("apCid",cId);
        ApplyPartTimeJob repeatedInfoInApplyPartTimeJob = applyPartTimeJobService.isRepeatedInfoInApplyPartTimeJob(detectRepeatMap);
        Map<String, Object> partTimeJob = partTimeJobService.fetchSpcPartTimeJobByPid(pId);
        PartTimeJob updatePartTimeJob = new PartTimeJob();
        updatePartTimeJob.setPId(Long.valueOf(partTimeJob.get("pId").toString()));
        updatePartTimeJob.setPAppointmentNum(Integer.parseInt(partTimeJob.get("pAppointmentNum").toString())+1);

        Util util = new Util();
        partTimeJob.put("pSubmitTime",util.computePageDays(partTimeJob.get("pSubmitTime").toString(),sdf));

        model.addAttribute("partTimeJob",partTimeJob);
        model.addAttribute("approximatePartTimeJobs",partTimeJobService.fetchApproximatePartTimeJobByPid(pId));

        if (!DOC_PATTERN.matcher(new Tika().detect(resumeFile.getInputStream(),new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, resumeFile.getOriginalFilename());}})).matches()&&
                !DOCX_PATTERN.matcher(new Tika().detect(resumeFile.getInputStream(),new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, resumeFile.getOriginalFilename());}})).matches() &&
                !PDF_PATTERN.matcher(new Tika().detect(resumeFile.getInputStream(),new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, resumeFile.getOriginalFilename());}})).matches()
        ){
            //            说明上传的不是word/pdf类型
//            model.addAttribute("approximatePartTimeJobs",partTimeJobService.fetchApproximatePartTimeJobByPid(pId));
            System.out.println(new Tika().detect(resumeFile.getInputStream(),new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, resumeFile.getOriginalFilename());}}));
            model.addAttribute("apMsg","请上传正确的简历文件格式(doc/docx/pdf)");
            return "zzy";
        }


        if(repeatedInfoInApplyPartTimeJob!=null){
            System.out.println("i came in repeated situation");
            model.addAttribute("apMsg","请勿重复申请同一个职位");
//            List<Map<String, Object>> approximatePartTimeJobs = partTimeJobService.fetchApproximatePartTimeJobByPid(pId);
//            model.addAttribute("approximatePartTimeJobs",approximatePartTimeJobs);
//            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            System.out.println(new Tika().detect(resumeFile.getInputStream(),new Metadata(){{add(Metadata.RESOURCE_NAME_KEY, resumeFile.getOriginalFilename());}}));
            System.out.println(model.getAttribute("apMsg"));
            return "zzy";
//            return "redirect:/PartTimeJob/fetchSpcPartTimeJob/"+pId;
        }


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


        partTimeJobService.updatePartTimeJob(updatePartTimeJob);
//        List<Map<String, Object>> approximatePartTimeJobs = partTimeJobService.fetchApproximatePartTimeJobByPid(pId);
//        model.addAttribute("approximatePartTimeJobs",approximatePartTimeJobs);
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

        Page<Map<String,Object>> page = new Page<Map<String,Object>>();
        page.setPageSize(PAGE_SIZE_THREE);
        if (redisUtils.zRange("AllPartTimeJobsByCidAndSearchInfo" + curAddPartTimeJobComSearchInfo+cId.toString(), 0, -1).size()!=partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo).size()){
            page.setTotal(partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo).size());

        }else {
            page.setTotal(redisUtils.zRange("AllPartTimeJobsByCidAndSearchInfo" + curAddPartTimeJobComSearchInfo+cId.toString(), 0,  -1).size());
        }
        if (pageNum>(page.getTotal()/PAGE_SIZE_THREE)){
            page.setPageNum(Double.valueOf(Math.ceil( (page.getTotal() / PAGE_SIZE_THREE.doubleValue()))).intValue());
            pageNum=Double.valueOf(Math.ceil( (page.getTotal() / PAGE_SIZE_THREE.doubleValue()))).intValue();
            System.out.println("PageNum:"+pageNum);
        }else {
            page.setPageNum(pageNum);
        }
        if (redisUtils.zRangeByScore("AllPartTimeJobsByCidAndSearchInfo" + curAddPartTimeJobComSearchInfo+cId.toString(),Integer.valueOf((pageNum - 1) * PAGE_SIZE_THREE).doubleValue(), Integer.valueOf((PAGE_SIZE_THREE *pageNum)-1).doubleValue())==null
                ||redisUtils.zRangeByScore("AllPartTimeJobsByCidAndSearchInfo" + curAddPartTimeJobComSearchInfo+cId.toString(),Integer.valueOf((pageNum - 1) * PAGE_SIZE_THREE).doubleValue(), Integer.valueOf((PAGE_SIZE_THREE *pageNum)-1).doubleValue()).isEmpty()
        ){
                    PageHelper.startPage(pageNum,PAGE_SIZE_THREE);
                    allPartTimeJobsByCidAndSearchInfo = partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo);
            int methodPageNum=(pageNum-1)*PAGE_SIZE_THREE;
            AtomicInteger finalMethodPageNum= new AtomicInteger(methodPageNum);
            String finalCurAddPartTimeJobComSearchInfo = curAddPartTimeJobComSearchInfo;
            allPartTimeJobsByCidAndSearchInfo.forEach(apjBCas->{
//                redisUtils.zAdd("AllPartTimeJobsByCidAndSearchInfo"+ finalCurAddPartTimeJobComSearchInfo+cId.toString(), JSON.toJSONString(apjBCas),Double.parseDouble(String.valueOf(finalMethodPageNum.get())));

                redisUtils.zAdd("AllPartTimeJobsByCidAndSearchInfo"+ finalCurAddPartTimeJobComSearchInfo+cId.toString(), apjBCas,Double.parseDouble(String.valueOf(finalMethodPageNum.get())));
                finalMethodPageNum.getAndIncrement();
                System.out.println(finalMethodPageNum);
            });
            redisUtils.expire("AllPartTimeJobsByCidAndSearchInfo" + curAddPartTimeJobComSearchInfo+cId.toString(),3600);
        }
//        allPartTimeJobsByCidAndSearchInfo=JSON.parseObject(redisUtils.zRangeByScore("AllPartTimeJobsByCidAndSearchInfo" + curAddPartTimeJobComSearchInfo+cId.toString(), Integer.valueOf((pageNum - 1) * PAGE_SIZE_THREE).doubleValue(), Integer.valueOf((PAGE_SIZE_THREE *pageNum)-1).doubleValue()).toString(), new TypeReference<List<Map<String, Object>>>() {
//        }.getType());

        allPartTimeJobsByCidAndSearchInfo=JSON.parseObject(JSON.toJSONString(redisUtils.zRangeByScore("AllPartTimeJobsByCidAndSearchInfo" + curAddPartTimeJobComSearchInfo+cId.toString(), Integer.valueOf((pageNum - 1) * PAGE_SIZE_THREE).doubleValue(), Integer.valueOf((PAGE_SIZE_THREE *pageNum)-1).doubleValue())), new TypeReference<List<Map<String, Object>>>() {
        }.getType());


        PageInfo<Map<String, Object>> allPartTimeJobsByCidAndSearchInfoPageInfo = new PageInfo<Map<String, Object>>(page);
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
        if (session.getAttribute("curAddPartTimeJobComSearchInfo")==null){
            session.setAttribute("curAddPartTimeJobComSearchInfo",STRING_NULL);
        }
        PartTimeJob addPartTimeJob = util.getPartTimeJobByPageParam(pagePartTimeJob);

        addPartTimeJob.setCId(cId);
        partTimeJobService.addPartTimeJob(addPartTimeJob);
        List<Map<String, Object>> allPartTimeJobsByCidAndSearchInfo=new ArrayList<Map<String, Object>>();
        Map<String, Object> cIdAndSearchInfo = new HashMap<String, Object>();
        cIdAndSearchInfo.put("cId",cId);
        cIdAndSearchInfo.put("partTimeJobSearchInfo",session.getAttribute("curAddPartTimeJobComSearchInfo").toString());


        Page<Map<String, Object>> page = new Page<Map<String, Object>>();
        page.setPageNum(DEFAULT_PAGE_COUNT);
        page.setPageSize(PAGE_SIZE_THREE);

        if (redisUtils.zRange("AllPartTimeJobsByCidAndSearchInfo" + session.getAttribute("curAddPartTimeJobComSearchInfo").toString()+cId.toString(), 0, -1).size()!=partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo).size()){
//            page.addAll(partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo));//将这里的page丢到最后的pageInfo里面
            page.setTotal(partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo).size());
        }else {
            System.out.println(redisUtils.zRange("AllPartTimeJobsByCidAndSearchInfo" + session.getAttribute("curAddPartTimeJobComSearchInfo").toString()+cId.toString(), 0, -1).size());
//            page.addAll(JSON.parseObject(redisUtils.zRange("AllPartTimeJobsByCidAndSearchInfo" + session.getAttribute("curAddPartTimeJobComSearchInfo").toString()+cId.toString(), 0, -1).toString(), new TypeReference<List<Map<String, Object>>>() {
//            }.getType()));//将这里的page丢到最后的pageInfo里面
            page.setTotal(redisUtils.zRange("AllPartTimeJobsByCidAndSearchInfo" + session.getAttribute("curAddPartTimeJobComSearchInfo").toString()+cId.toString(), 0,  -1).size());
        }

        if (redisUtils.zRangeByScore("AllPartTimeJobsByCidAndSearchInfo" + session.getAttribute("curAddPartTimeJobComSearchInfo").toString()+cId.toString(),0, 2)==null
                ||redisUtils.zRangeByScore("AllPartTimeJobsByCidAndSearchInfo" + session.getAttribute("curAddPartTimeJobComSearchInfo").toString()+cId.toString(),0, 2).isEmpty()
        ){
            PageHelper.startPage(DEFAULT_PAGE_COUNT,PAGE_SIZE_THREE);
             allPartTimeJobsByCidAndSearchInfo = partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo);
            AtomicInteger i=new AtomicInteger(0);
            allPartTimeJobsByCidAndSearchInfo.forEach(apjBCas->{
                redisUtils.zAdd("AllPartTimeJobsByCidAndSearchInfo"+ session.getAttribute("curAddPartTimeJobComSearchInfo").toString()+cId.toString(), apjBCas,Double.parseDouble(String.valueOf(i.get())));
                i.getAndIncrement();
                System.out.println(i);
            });
            redisUtils.expire("AllPartTimeJobsByCidAndSearchInfo" + session.getAttribute("curAddPartTimeJobComSearchInfo").toString()+cId.toString(),3600);
        }


        allPartTimeJobsByCidAndSearchInfo=JSON.parseObject(JSON.toJSONString(redisUtils.zRange("AllPartTimeJobsByCidAndSearchInfo" + session.getAttribute("curAddPartTimeJobComSearchInfo").toString()+cId.toString(), 0, -1)), new TypeReference<List<Map<String, Object>>>() {
        }.getType());
//        PageInfo<Map<String, Object>> allPartTimeJobsByCidAndSearchInfoPageInfo = new PageInfo<Map<String, Object>>(allPartTimeJobsByCidAndSearchInfo);
        PageInfo<Map<String, Object>> allPartTimeJobsByCidAndSearchInfoPageInfo = new PageInfo<Map<String, Object>>(page);

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
