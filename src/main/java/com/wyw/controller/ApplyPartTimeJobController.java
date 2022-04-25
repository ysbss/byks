package com.wyw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyw.service.ApplyPartTimeJobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Controller
@RequestMapping("/ApplyPartTimeJob")
public class ApplyPartTimeJobController {
    @Resource
    ApplyPartTimeJobService applyPartTimeJobService;

    @RequestMapping("/fetchApplyPartTimeJobsBySidAndSearchInfo/{sId}/{pageNum}")
    public String fetchApplyPartTimeJobsBySidAndSearchInfo(@RequestParam(required = false) String pageApplyPartTimeJobStuSearchInfo,
                                         Model model,
                                         HttpSession session,
                                         @PathVariable(value = "sId")Long sId,
                                         @PathVariable(value = "pageNum",required = false) Integer pageNum){
        PageHelper.startPage(pageNum,3);
        List<Map<String, Object>> allApplyPartTimeJobs= new ArrayList<Map<String, Object>>();
        String curApplyPartTimeJobStuSearchInfo = session.getAttribute("curApplyPartTimeJobStuSearchInfo").toString();

        Map<String, Object> sIdAndSearchInfo = new HashMap<String,Object>();
        sIdAndSearchInfo.put("apSid",sId);
        if (pageApplyPartTimeJobStuSearchInfo!=null){
            curApplyPartTimeJobStuSearchInfo=pageApplyPartTimeJobStuSearchInfo;
            session.setAttribute("curApplyPartTimeJobStuSearchInfo",pageApplyPartTimeJobStuSearchInfo);
        }
        sIdAndSearchInfo.put("applyPartTimeJobSearchInfo",curApplyPartTimeJobStuSearchInfo);
        allApplyPartTimeJobs = applyPartTimeJobService.fetchApplyPartTimeJobsBysIdAndSearchInfo(sIdAndSearchInfo);
        PageInfo<Map<String, Object>> allApplyPartTimeJobsPageInfo = new PageInfo<Map<String, Object>>(allApplyPartTimeJobs);
        model.addAttribute("allApplyPartTimeJobs",allApplyPartTimeJobs);
        model.addAttribute("allApplyPartTimeJobsPageInfo",allApplyPartTimeJobsPageInfo);
//        model.addAttribute("sId",sId);
        return "stuApplyPartTimeJobList";
    }


    @RequestMapping("/fetchApplyPartTimeJobsByCidAndSearchInfo/{cId}/{pageNum}")
    public String fetchApplyPartTimeJobsByCidAndSearchInfo(@RequestParam(required = false) String pageApplyPartTimeJobComSearchInfo,
                                                           Model model,
                                                           HttpSession session,
                                                           @PathVariable(value = "cId")Long cId,
                                                           @PathVariable(value = "pageNum",required = false) Integer pageNum){
        PageHelper.startPage(pageNum,3);
        List<Map<String, Object>> allApplyPartTimeJobs=new ArrayList<Map<String, Object>>();
        String curApplyPartTimeJobComSearchInfo = session.getAttribute("curApplyPartTimeJobComSearchInfo").toString();
        Map<String, Object> cIdAndSearchInfo = new HashMap<String,Object>();
        cIdAndSearchInfo.put("apCid",cId);
        if (pageApplyPartTimeJobComSearchInfo!=null){
            curApplyPartTimeJobComSearchInfo=pageApplyPartTimeJobComSearchInfo;
            session.setAttribute("curApplyPartTimeJobComSearchInfo",pageApplyPartTimeJobComSearchInfo);
        }
        cIdAndSearchInfo.put("applyPartTimeJobSearchInfo",curApplyPartTimeJobComSearchInfo);
         allApplyPartTimeJobs = applyPartTimeJobService.fetchApplyPartTimeJobsBycIdAndSearchInfo(cIdAndSearchInfo);
        PageInfo<Map<String, Object>> allApplyPartTimeJobsPageInfo = new PageInfo<Map<String, Object>>(allApplyPartTimeJobs);
        model.addAttribute("allApplyPartTimeJobs",allApplyPartTimeJobs);
        model.addAttribute("allApplyPartTimeJobsPageInfo",allApplyPartTimeJobsPageInfo);
//        model.addAttribute("cId",cId);
        return "comApplyPartTimeJobList";
    }



    @RequestMapping("/comDealApplyPartTimeJob/{cId}/{pageNum}")
    public String comDealApplyPartTimeJob(@RequestParam(required = false) String pageApplyPartTimeJobComSearchInfo,
                                          @RequestParam Long pageApId,
                                          @RequestParam Integer pageDealStatus,
                                          Model model,
                                          HttpSession session,
                                          @PathVariable(value = "cId")Long cId,
                                          @PathVariable(value = "pageNum",required = false) Integer pageNum){
        Map<String, Object> comDealMap = new HashMap<String, Object>();
        comDealMap.put("apId",pageApId);
        comDealMap.put("apStatus",pageDealStatus);
        applyPartTimeJobService.comDealApplyPartTimeJob(comDealMap);
        PageHelper.startPage(pageNum,3);

        List<Map<String, Object>> allApplyPartTimeJobs=new ArrayList<Map<String, Object>>();
        String curApplyPartTimeJobComSearchInfo = session.getAttribute("curApplyPartTimeJobComSearchInfo").toString();
        Map<String, Object> cIdAndSearchInfo = new HashMap<String,Object>();
        cIdAndSearchInfo.put("apCid",cId);
        if (pageApplyPartTimeJobComSearchInfo!=null){
            curApplyPartTimeJobComSearchInfo=pageApplyPartTimeJobComSearchInfo;
            session.setAttribute("curApplyPartTimeJobComSearchInfo",pageApplyPartTimeJobComSearchInfo);
        }

        cIdAndSearchInfo.put("applyPartTimeJobSearchInfo",curApplyPartTimeJobComSearchInfo);
        allApplyPartTimeJobs = applyPartTimeJobService.fetchApplyPartTimeJobsBycIdAndSearchInfo(cIdAndSearchInfo);
        PageInfo<Map<String, Object>> allApplyPartTimeJobsPageInfo = new PageInfo<Map<String, Object>>(allApplyPartTimeJobs);
        model.addAttribute("allApplyPartTimeJobs",allApplyPartTimeJobs);
        model.addAttribute("allApplyPartTimeJobsPageInfo",allApplyPartTimeJobsPageInfo);
//        model.addAttribute("cId",cId);

        return "comApplyPartTimeJobList";
    }
}
