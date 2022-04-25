package com.wyw.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyw.service.ApplyServiceProvideService;
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
@RequestMapping("/ApplyServiceProvide")
public class ApplyServiceProvideController {

    @Resource
    ApplyServiceProvideService applyServiceProvideService;






    @RequestMapping("/fetchApplyServiceProvidesBysIdAndSearchInfo/{sId}/{pageNum}")
    public String fetchApplyServiceProvidesBysIdAndSearchInfo(@RequestParam(required = false) String pageApplyServiceProvideStuSearchInfo,
                                                              Model model,
                                                              HttpSession session,
                                                              @PathVariable(value = "sId")Long sId,
                                                              @PathVariable(value = "pageNum",required = false) Integer pageNum){

        PageHelper.startPage(pageNum,3);
        List<Map<String, Object>> allApplyServiceProvides=new ArrayList<Map<String, Object>>();
        String curApplyServiceProvideStuSearchInfo = session.getAttribute("curApplyServiceProvideStuSearchInfo").toString();
        Map<String, Object> sIdAndSearchInfo = new HashMap<String,Object>();
        sIdAndSearchInfo.put("aspSid",sId);
        if (pageApplyServiceProvideStuSearchInfo!=null){
            curApplyServiceProvideStuSearchInfo=pageApplyServiceProvideStuSearchInfo;
            session.setAttribute("curApplyServiceProvideStuSearchInfo",pageApplyServiceProvideStuSearchInfo);
        }
        sIdAndSearchInfo.put("applyServiceProvidesSearchInfo",curApplyServiceProvideStuSearchInfo);
        allApplyServiceProvides=applyServiceProvideService.fetchApplyServiceProvidesBysIdAndSearchInfo(sIdAndSearchInfo);
        PageInfo<Map<String, Object>> allApplyServiceProvidesPageInfo = new PageInfo<Map<String, Object>>(allApplyServiceProvides);
        model.addAttribute("allApplyServiceProvides",allApplyServiceProvides);
        model.addAttribute("allApplyServiceProvidesPageInfo",allApplyServiceProvidesPageInfo);

        return "stuApplyServiceProvideList";
    }

    @RequestMapping("/fetchApplyServiceProvidesBycIdAndSearchInfo/{cId}/{pageNum}")
    public String fetchApplyServiceProvidesBycIdAndSearchInfo(@RequestParam(required = false) String pageApplyServiceProvideComSearchInfo,
                                                              Model model,
                                                              HttpSession session,
                                                              @PathVariable(value = "cId")Long cId,
                                                              @PathVariable(value = "pageNum",required = false) Integer pageNum){

        PageHelper.startPage(pageNum,3);
        List<Map<String, Object>> allApplyServiceProvides=new ArrayList<Map<String, Object>>();
        String curApplyServiceProvideComSearchInfo = session.getAttribute("curApplyServiceProvideComSearchInfo").toString();
        Map<String, Object> cIdAndSearchInfo = new HashMap<String,Object>();
        cIdAndSearchInfo.put("aspCid",cId);
        if (pageApplyServiceProvideComSearchInfo!=null){
            curApplyServiceProvideComSearchInfo=pageApplyServiceProvideComSearchInfo;
            session.setAttribute("curApplyServiceProvideComSearchInfo",pageApplyServiceProvideComSearchInfo);
        }
        cIdAndSearchInfo.put("applyServiceProvidesSearchInfo",curApplyServiceProvideComSearchInfo);
        allApplyServiceProvides=applyServiceProvideService.fetchApplyServiceProvidesBycIdAndSearchInfo(cIdAndSearchInfo);
        PageInfo<Map<String, Object>> allApplyServiceProvidesPageInfo = new PageInfo<Map<String, Object>>(allApplyServiceProvides);
        model.addAttribute("allApplyServiceProvides",allApplyServiceProvides);
        model.addAttribute("allApplyServiceProvidesPageInfo",allApplyServiceProvidesPageInfo);

        return "comApplyServiceProvideList";
    }


    @RequestMapping("/comDealApplyServiceProvide/{cId}/{pageNum}")
    public String comDealApplyServiceProvide(@RequestParam(required = false) String pageApplyServiceProvideComSearchInfo,
                                        @RequestParam Long pageAspId,
                                        @RequestParam Integer pageDealStatus,
                                        Model model,
                                        HttpSession session,
                                        @PathVariable(value = "cId")Long cId,
                                        @PathVariable(value = "pageNum",required = false) Integer pageNum){



        Map<String, Object> comDealMap = new HashMap<String, Object>();
        comDealMap.put("aspId",pageAspId);
        comDealMap.put("aspStatus",pageDealStatus);
        applyServiceProvideService.comDealApplyServiceProvide(comDealMap);

        PageHelper.startPage(pageNum,3);
        List<Map<String, Object>> allApplyServiceProvides=new ArrayList<Map<String, Object>>();
        String curApplyServiceProvideComSearchInfo = session.getAttribute("curApplyServiceProvideComSearchInfo").toString();
        Map<String, Object> cIdAndSearchInfo = new HashMap<String,Object>();
        cIdAndSearchInfo.put("aspCid",cId);
        if (pageApplyServiceProvideComSearchInfo!=null){
            curApplyServiceProvideComSearchInfo=pageApplyServiceProvideComSearchInfo;
            session.setAttribute("curApplyServiceProvideComSearchInfo",pageApplyServiceProvideComSearchInfo);
        }
        cIdAndSearchInfo.put("applyServiceProvidesSearchInfo",curApplyServiceProvideComSearchInfo);
        allApplyServiceProvides=applyServiceProvideService.fetchApplyServiceProvidesBycIdAndSearchInfo(cIdAndSearchInfo);
        PageInfo<Map<String, Object>> allApplyServiceProvidesPageInfo = new PageInfo<Map<String, Object>>(allApplyServiceProvides);
        model.addAttribute("allApplyServiceProvides",allApplyServiceProvides);
        model.addAttribute("allApplyServiceProvidesPageInfo",allApplyServiceProvidesPageInfo);

        return "comApplyServiceProvideList";
    }

}
