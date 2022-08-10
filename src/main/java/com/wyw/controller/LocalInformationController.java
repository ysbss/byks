package com.wyw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyw.pojo.ApplyLocalInformation;
import com.wyw.pojo.LocalInformation;
import com.wyw.service.ApplyLocalInformationService;
import com.wyw.service.LocalInformationService;
import com.wyw.utils.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wyw.utils.FinalStaticValue.*;

/**
 * @author 鱼酥不是叔
 */
@Controller
@RequestMapping("/LocalInformation")
public class LocalInformationController {
    @Resource
    LocalInformationService localInformationService;

    @Resource
    ApplyLocalInformationService applyLocalInformationService;

    @RequestMapping("/fetchLocalInformationList/{pageNum}")
    public String fetchLocalInformationList(            @RequestParam(required = false) String pageLocalInformationSearchInfo,
                                                        @RequestParam(required = false) Integer lKind,
                                                        @RequestParam(required = false)String lSpecificKind ,
                                                        @PathVariable(value = "pageNum",required = false) Integer pageNum,
                                                        Model model,
                                                        HttpSession session){

        List<Integer> localInformationKinds = localInformationService.fetchLocalInformationKinds();
        List<Map<String, Object>> localInformationSpecificKinds = localInformationService.fetchLocalInformationSpecificKinds();
        List<Map<String, Object>> homePageLocalInformation = localInformationService.fetchHomePageLocalInformation();

        HashMap<String, Object> searchMap = new HashMap<String,Object>();
        if (lKind==null && lSpecificKind==null ){
            //说明点击了搜索，直接从所有里面搜索,直接走搜索的结果
            System.out.println("我要进来");
            session.setAttribute("curLocalInformationSearchInfo",STRING_NULL);
            session.setAttribute("curSpKind",INTEGER_NULL);
            session.setAttribute("curSpSpecificKind",STRING_NULL);
        }
        String curLocalInformationSearchInfo = session.getAttribute("curLocalInformationSearchInfo").toString();
        if (pageLocalInformationSearchInfo!=null ){
            curLocalInformationSearchInfo=pageLocalInformationSearchInfo;
            session.setAttribute("curLocalInformationSearchInfo",pageLocalInformationSearchInfo);
        }
        searchMap.put("localInformationSearchInfo",curLocalInformationSearchInfo);
        searchMap.put("curLKind",session.getAttribute("curSpKind"));
        searchMap.put("curLSpecificKind",session.getAttribute("curSpSpecificKind"));


        PageHelper.startPage(pageNum,5);
        List<Map<String, Object>> allLocalKindInformation = localInformationService.fetchAllLocalInformation(searchMap);
        PageInfo<Map<String,Object>> allLocalKindInformationPageInfo=new PageInfo<Map<String,Object>>(allLocalKindInformation);

        model.addAttribute("localInformationKinds",localInformationKinds);
        model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
        model.addAttribute("homePageLocalInformation",homePageLocalInformation);

        model.addAttribute("allLocalKindInformation",allLocalKindInformation);
        model.addAttribute("allLocalKindInformationPageInfo",allLocalKindInformationPageInfo);


        return "bj";
    }



    @RequestMapping("/applyLocalInformation/{lId}/{lCid}")
    public String applyLocalInformation(Model model,
                                        @PathVariable(value = "lId",required = false) Long lId,
                                        @PathVariable(value = "lCid",required = false) Long lCid,
                                        HttpSession session) throws ParseException {
        Map<String, Object> detectMap = new HashMap<String,Object>();
        detectMap.put("aliLiId",lId);
        detectMap.put("aliSid",Long.valueOf(session.getAttribute("currentStuId").toString()));
        detectMap.put("aspCid",lCid);
        ApplyLocalInformation repeatedInfoInApplyLocalInformation = applyLocalInformationService.isRepeatedInfoInApplyLocalInformation(detectMap);

        Util util = new Util();


        Map<String, Object> localInformation = localInformationService.fetchLocalInformationBylId(lId);

        Map<String,Object> lKindAndLid=new HashMap<String,Object>();

        lKindAndLid.put("lId",lId);
        lKindAndLid.put("lKind",Integer.valueOf(localInformation.get("lKind").toString()));

        List<Map<String, Object>> approximateLocalInformation = localInformationService.fetchApproximateLocalInformationBylKindAndLid(lKindAndLid);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        localInformation.put("lSubmitTime",util.computePageDays(localInformation.get("lSubmitTime").toString(),sdf));


        model.addAttribute("approximateLocalInformation",approximateLocalInformation);
        model.addAttribute("localInformation",localInformation);


        if (repeatedInfoInApplyLocalInformation!=null){
            model.addAttribute("apMsg","请勿重复申请同一个职位");
            return "localInformation";
        }
        ApplyLocalInformation applyLocalInformation = new ApplyLocalInformation();
        applyLocalInformation.setAliLiId(lId);
        applyLocalInformation.setAliSid(Long.valueOf(session.getAttribute("currentStuId").toString()));
        applyLocalInformation.setAliCid(lCid);
        applyLocalInformation.setAliSubmitTime(util.CSTDateFormatFromPageTransferToString(sdf.parse(sdf.format(System.currentTimeMillis())),sdf));
        applyLocalInformation.setAliStatus(UN_DISPOSED_STATUS);
        applyLocalInformationService.addApplyLocalInformation(applyLocalInformation);
        localInformationService.updateLocalInformationAppointmentByLid(lId);
        model.addAttribute("apMsg","申请成功");




        return "localInformation";
    }


    @RequestMapping("/comFetchLocalInformationList/{cId}/{pageNum}")
    public String comFetchLocalInformationList(@RequestParam(required = false) String pageAddLocalInformationComSearchInfo,
                                               Model model,
                                               HttpSession session,
                                               @PathVariable(value = "cId")Long cId,
                                               @PathVariable(value = "pageNum",required = false) Integer pageNum){



        PageHelper.startPage(pageNum,3);

        if(session.getAttribute("curAddLocalInformationComSearchInfo")==null){
            session.setAttribute("curAddLocalInformationComSearchInfo",STRING_NULL);
        }

        String curAddLocalInformationComSearchInfo = session.getAttribute("curAddLocalInformationComSearchInfo").toString();
        Map<String,Object> searchInfo = new HashMap<String, Object>();
        List<Map<String, Object>> allLocalInformation=new ArrayList<Map<String, Object>>();
        searchInfo.put("lCid",cId);
        if (pageAddLocalInformationComSearchInfo!=null){
            curAddLocalInformationComSearchInfo=pageAddLocalInformationComSearchInfo;
            session.setAttribute("curAddLocalInformationComSearchInfo",pageAddLocalInformationComSearchInfo);
        }
        searchInfo.put("localInformationSearchInfo",curAddLocalInformationComSearchInfo);

        allLocalInformation = localInformationService.fetchAllLocalInformation(searchInfo);
        PageInfo<Map<String, Object>> allLocalInformationPageInfo=new PageInfo<Map<String, Object>>(allLocalInformation);
        model.addAttribute("allLocalInformation",allLocalInformation);
        model.addAttribute("allLocalInformationPageInfo",allLocalInformationPageInfo);

        return "comAddLocalInformationList";
    }

    @RequestMapping("/comDeleteLocalInformationByLid/{cId}/{pageNum}")
    public String comDeleteLocalInformationByLid(@RequestParam(required = false) String pageAddLocalInformationComSearchInfo,
                                                 Model model,
                                                 HttpSession session,
                                                 @RequestParam(required = false) Long pageLid,
                                                 @PathVariable(value = "cId")Long cId,
                                                 @PathVariable(value = "pageNum",required = false) Integer pageNum){


        LocalInformation updateLocalInformation = new LocalInformation();
        updateLocalInformation.setLId(pageLid);
        updateLocalInformation.setLSpcFlag(DELETED_FLAG);
        localInformationService.updateLocalInformation(updateLocalInformation);
        Map<String,Object> comDealMap = new HashMap<String,Object>();
        comDealMap.put("aliLiId",pageLid);
        comDealMap.put("aliStatus",DEPRECATED_STATUS);
        applyLocalInformationService.comDealApplyLocalInformation(comDealMap);



        PageHelper.startPage(pageNum,3);

        if(session.getAttribute("curAddLocalInformationComSearchInfo")==null){
            session.setAttribute("curAddLocalInformationComSearchInfo",STRING_NULL);
        }

        String curAddLocalInformationComSearchInfo = session.getAttribute("curAddLocalInformationComSearchInfo").toString();
        Map<String,Object> searchInfo = new HashMap<String, Object>();
        List<Map<String, Object>> allLocalInformation=new ArrayList<Map<String, Object>>();
        searchInfo.put("lCid",cId);
        if (pageAddLocalInformationComSearchInfo!=null){
            curAddLocalInformationComSearchInfo=pageAddLocalInformationComSearchInfo;
            session.setAttribute("curAddLocalInformationComSearchInfo",pageAddLocalInformationComSearchInfo);
        }
        searchInfo.put("localInformationSearchInfo",curAddLocalInformationComSearchInfo);

        allLocalInformation = localInformationService.fetchAllLocalInformation(searchInfo);
        PageInfo<Map<String, Object>> allLocalInformationPageInfo=new PageInfo<Map<String, Object>>(allLocalInformation);
        model.addAttribute("allLocalInformation",allLocalInformation);
        model.addAttribute("allLocalInformationPageInfo",allLocalInformationPageInfo);
        return "comAddLocalInformationList";
    }


    @RequestMapping("/comAddLocalInformation/{cId}")
    public String comAddLocalInformation(@RequestParam Map<String,Object> pageLocalInformation,
                                         @PathVariable(value = "cId")Long cId,
                                         HttpSession session,
                                         Model model) throws ParseException {
        Util util = new Util();
        System.out.println(pageLocalInformation.get("lSpecificKind").toString().replaceAll("\'",""));
        int flag=util.isLegalInputLocalInformationMap(pageLocalInformation);
        if (flag==EMPTY_POJO){
            model.addAttribute("addMsg","请输入所有信息");
            return "comAddLocalInformation";
        }
//        pageLocalInformation.put("lKind",localInformationService.fetchLocalInformationKindBySpecificKind(pageLocalInformation.get("lSpecificKind").toString().replaceAll("\'","")));
        LocalInformation addLocalInformation = util.getLocalInformationByPageParam(pageLocalInformation);
        addLocalInformation.setLCid(cId);

        localInformationService.addLocalInformation(addLocalInformation);

        PageHelper.startPage(1,3);
        Map<String,Object> searchInfo = new HashMap<String, Object>();
        searchInfo.put("lCid",cId);
        if (session.getAttribute("curAddLocalInformationComSearchInfo")!=null){
            session.setAttribute("curAddLocalInformationComSearchInfo",STRING_NULL);
        }
        searchInfo.put("localInformationSearchInfo",session.getAttribute("curAddLocalInformationComSearchInfo").toString());

        List<Map<String, Object>> allLocalInformation = localInformationService.fetchAllLocalInformation(searchInfo);
        PageInfo<Map<String, Object>> allLocalInformationPageInfo=new PageInfo<Map<String, Object>>(allLocalInformation);
        model.addAttribute("allLocalInformation",allLocalInformation);
        model.addAttribute("allLocalInformationPageInfo",allLocalInformationPageInfo);

        return "comAddLocalInformationList";
    }


    @RequestMapping("/comUpdateLocalInformation/{lId}")
    public String comUpdateLocalInformation(@RequestParam Map<String,Object> pageLocalInformation,
                                            @PathVariable(value = "lId")Long lId,
                                            Model model) throws ParseException {
        System.out.println(pageLocalInformation.get("lSpecificKind").toString().replaceAll("\'",""));
        Util util = new Util();
        int flag= util.isLegalInputLocalInformationMap(pageLocalInformation);
        if (flag==EMPTY_POJO){
            model.addAttribute("updateMsg","请输入所有信息");
            model.addAttribute("localInformation",localInformationService.fetchLocalInformationBylId(lId));
            model.addAttribute("defaultSpecificKinds",localInformationService.fetchLocalInformationSpecificKindsByLocalInformationKind(Integer.valueOf(localInformationService.fetchLocalInformationBylId(lId).get("lKind").toString())));

            return "comUpdateLocalInformation";
        }

        pageLocalInformation.put("lId",lId);
        localInformationService.updateLocalInformation(util.getLocalInformationByPageParam(pageLocalInformation));

        model.addAttribute("localInformation",localInformationService.fetchLocalInformationBylId(lId));
        model.addAttribute("updateMsg","更新成功");
        model.addAttribute("defaultSpecificKinds",localInformationService.fetchLocalInformationSpecificKindsByLocalInformationKind(Integer.valueOf(localInformationService.fetchLocalInformationBylId(lId).get("lKind").toString())));

        return "comUpdateLocalInformation";
    }


    @ResponseBody
    @RequestMapping("/comSelectLocalInformationKind")
    public List<String> comSelectLocalInformationKind(Integer lKind){
        System.out.println(localInformationService.fetchLocalInformationSpecificKindsByLocalInformationKind(lKind));
        return localInformationService.fetchLocalInformationSpecificKindsByLocalInformationKind(lKind);
    }


}
