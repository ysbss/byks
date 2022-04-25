package com.wyw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyw.service.ApplyLocalInformationService;
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
@RequestMapping("/ApplyLocalInformation")
public class ApplyLocalInformationController {

    @Resource
    ApplyLocalInformationService applyLocalInformationService;

    @RequestMapping("/fetchApplyLocalInformationBysIdAndSearchInfo/{sId}/{pageNum}")
    public String fetchApplyLocalInformationBysIdAndSearchInfo(@RequestParam(required = false) String pageApplyLocalInformationStuSearchInfo,
                                                               Model model,
                                                               HttpSession session,
                                                               @PathVariable(value = "sId")Long sId,
                                                               @PathVariable(value = "pageNum",required = false) Integer pageNum){

        PageHelper.startPage(pageNum,3);
        List<Map<String, Object>> allApplyLocalInformation=new ArrayList<Map<String, Object>>();
        String curApplyLocalInformationStuSearchInfo = session.getAttribute("curApplyLocalInformationStuSearchInfo").toString();
        Map<String, Object> sIdAndSearchInfo = new HashMap<String,Object>();
        sIdAndSearchInfo.put("aliSid",sId);
        if (pageApplyLocalInformationStuSearchInfo!=null){
            curApplyLocalInformationStuSearchInfo=pageApplyLocalInformationStuSearchInfo;
            session.setAttribute("curApplyLocalInformationStuSearchInfo",pageApplyLocalInformationStuSearchInfo);
        }
        sIdAndSearchInfo.put("applyLocalInformationSearchInfo",curApplyLocalInformationStuSearchInfo);
        allApplyLocalInformation=applyLocalInformationService.fetchApplyLocalInformationBysIdAndSearchInfo(sIdAndSearchInfo);
        PageInfo<Map<String, Object>> allApplyLocalInformationPageInfo = new PageInfo<Map<String, Object>>(allApplyLocalInformation);
        model.addAttribute("allApplyLocalInformation",allApplyLocalInformation);
        model.addAttribute("allApplyLocalInformationPageInfo",allApplyLocalInformationPageInfo);


        return "stuApplyLocalInformationList";
    }




    @RequestMapping("/fetchApplyLocalInformationBycIdAndSearchInfo/{cId}/{pageNum}")
    public String fetchApplyLocalInformationBycIdAndSearchInfo(@RequestParam(required = false) String pageApplyLocalInformationComSearchInfo,
                                                               Model model,
                                                               HttpSession session,
                                                               @PathVariable(value = "cId")Long cId,
                                                               @PathVariable(value = "pageNum",required = false) Integer pageNum){

        PageHelper.startPage(pageNum,3);
        List<Map<String, Object>> allApplyLocalInformation=new ArrayList<Map<String, Object>>();
        String curApplyLocalInformationComSearchInfo = session.getAttribute("curApplyLocalInformationComSearchInfo").toString();
        Map<String, Object> cIdAndSearchInfo = new HashMap<String,Object>();
        cIdAndSearchInfo.put("aliCid",cId);
        if (pageApplyLocalInformationComSearchInfo!=null){
            curApplyLocalInformationComSearchInfo=pageApplyLocalInformationComSearchInfo;
            session.setAttribute("curApplyLocalInformationComSearchInfo",pageApplyLocalInformationComSearchInfo);
        }

        cIdAndSearchInfo.put("applyLocalInformationSearchInfo",curApplyLocalInformationComSearchInfo);

        allApplyLocalInformation=applyLocalInformationService.fetchApplyLocalInformationBycIdAndSearchInfo(cIdAndSearchInfo);

        PageInfo<Map<String, Object>> allApplyLocalInformationPageInfo = new PageInfo<Map<String, Object>>(allApplyLocalInformation);
        model.addAttribute("allApplyLocalInformation",allApplyLocalInformation);
        model.addAttribute("allApplyLocalInformationPageInfo",allApplyLocalInformationPageInfo);


        return "comApplyLocalInformationList";
    }

    @RequestMapping("/comDealApplyLocalInformation/{cId}/{pageNum}")
    public String comDealApplyLocalInformation(@RequestParam(required = false) String pageApplyLocalInformationComSearchInfo,
                                               @RequestParam Long pageAliId,
                                               @RequestParam Integer pageDealStatus,
                                               Model model,
                                               HttpSession session,
                                               @PathVariable(value = "cId")Long cId,
                                               @PathVariable(value = "pageNum",required = false) Integer pageNum){
        Map<String, Object> comDealMap = new HashMap<String, Object>();
        comDealMap.put("aliId",pageAliId);
        comDealMap.put("aliStatus",pageDealStatus);
        applyLocalInformationService.comDealApplyLocalInformation(comDealMap);

        PageHelper.startPage(pageNum,3);
        List<Map<String, Object>> allApplyLocalInformation=new ArrayList<Map<String, Object>>();
        String curApplyLocalInformationComSearchInfo = session.getAttribute("curApplyLocalInformationComSearchInfo").toString();
        Map<String, Object> cIdAndSearchInfo = new HashMap<String,Object>();
        cIdAndSearchInfo.put("aliCid",cId);
        if (pageApplyLocalInformationComSearchInfo!=null){
            curApplyLocalInformationComSearchInfo=pageApplyLocalInformationComSearchInfo;
            session.setAttribute("curApplyLocalInformationComSearchInfo",pageApplyLocalInformationComSearchInfo);
        }

        cIdAndSearchInfo.put("applyLocalInformationSearchInfo",curApplyLocalInformationComSearchInfo);

        allApplyLocalInformation=applyLocalInformationService.fetchApplyLocalInformationBycIdAndSearchInfo(cIdAndSearchInfo);

        PageInfo<Map<String, Object>> allApplyLocalInformationPageInfo = new PageInfo<Map<String, Object>>(allApplyLocalInformation);
        model.addAttribute("allApplyLocalInformation",allApplyLocalInformation);
        model.addAttribute("allApplyLocalInformationPageInfo",allApplyLocalInformationPageInfo);


        return "comApplyLocalInformationList";
    }




}
