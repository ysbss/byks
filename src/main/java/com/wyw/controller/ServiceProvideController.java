package com.wyw.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyw.pojo.ApplyServiceProvide;
import com.wyw.pojo.ServiceProvide;
import com.wyw.service.ApplyServiceProvideService;
import com.wyw.service.ServiceProvideService;
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
@RequestMapping("/ServiceProvide")
public class ServiceProvideController {

    @Resource
    ServiceProvideService serviceProvideService;

    @Resource
    ApplyServiceProvideService applyServiceProvideService;



//    @RequestMapping({"/fetchServiceProvidesList/{pageNum}/{spKind}/{spSpecificKind}","/fetchServiceProvidesList/{pageNum}/{spKind}","/fetchServiceProvidesList/{pageNum}/{spSpecificKind}"})
    @RequestMapping("/fetchServiceProvidesList/{pageNum}")
    public String fetchServiceProvidesList(
            @RequestParam(required = false) String pageServiceProvideSearchInfo,
            @RequestParam(required = false) Integer spKind,
            @RequestParam(required = false)String spSpecificKind ,
            @PathVariable(value = "pageNum",required = false) Integer pageNum,
            Model model,
            HttpSession session

    ){

        List<Integer> serviceProvideKinds = serviceProvideService.fetchServiceProvideKinds();
        List<Map<String, Object>> serviceProvideSpecificKinds = serviceProvideService.fetchServiceProvideSpecificKinds();
        List<Map<String, Object>> homePageServiceProvides = serviceProvideService.fetchHomePageServiceProvides();

        HashMap<String, Object> searchMap = new HashMap<String,Object>();

        System.out.println(spKind);
        System.out.println(spSpecificKind);
        if (spKind==null && spSpecificKind==null ){
            //说明点击了搜索，直接从所有里面搜索,直接走搜索的结果
            System.out.println("我要进来");
            session.setAttribute("curSpKind",INTEGER_NULL);
            session.setAttribute("curSpSpecificKind",STRING_NULL);
        }
        String curServiceProvideSearchInfo = session.getAttribute("curServiceProvideSearchInfo").toString();
        if (pageServiceProvideSearchInfo!=null ){
            curServiceProvideSearchInfo=pageServiceProvideSearchInfo;
            session.setAttribute("curServiceProvideSearchInfo",pageServiceProvideSearchInfo);
        }
        searchMap.put("serviceProvideSearchInfo",curServiceProvideSearchInfo);
        searchMap.put("spKind",session.getAttribute("curSpKind"));
        searchMap.put("spSpecificKind",session.getAttribute("curSpSpecificKind"));




        PageHelper.startPage(pageNum,5);
        List<Map<String, Object>> allServiceProvides = serviceProvideService.fetchAllServiceProvides(searchMap);
        PageInfo<Map<String,Object>> allServiceProvidesPageInfo=new PageInfo<Map<String,Object>>(allServiceProvides);


        model.addAttribute("serviceProvideKinds",serviceProvideKinds);
        model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
        model.addAttribute("homePageServiceProvides",homePageServiceProvides);

        model.addAttribute("allServiceProvides",allServiceProvides);
        model.addAttribute("allServiceProvidesPageInfo",allServiceProvidesPageInfo);


//        if(session.getAttribute("curServiceProvideSearchInfo")==null){
//            session.setAttribute("curServiceProvideSearchInfo",STRING_NULL);
//        }
//        if(session.getAttribute("curSpKind")==null){
//            session.setAttribute("curSpKind",INTEGER_NULL);
//        }
//        if (session.getAttribute("curSpSpecificKind")==null){
//            session.setAttribute("curSpSpecificKind",STRING_NULL);
//        }
//        //上面三个判断session里是否有三个kv值map
//        String curPageServiceProvideSearchInfo=session.getAttribute("curServiceProvideSearchInfo").toString();
//        System.out.println(session.getAttribute("curSpKind"));
//        System.out.println(spSpecificKind);
//        if("".equals(spSpecificKind)){
//            System.out.println("求求你进来吧");
//            session.setAttribute("curSpSpecificKind",STRING_NULL);
//        }
//
//
//        if(session.getAttribute("curSpKind")!=null){
//             curSpKind = Integer.valueOf(session.getAttribute("curSpKind").toString());
//        }
//        if (session.getAttribute("curSpSpecificKind")!=null){
//            curSpSpecificKind = session.getAttribute("curSpSpecificKind").toString();
//        }
//
//        if (pageServiceProvideSearchInfo!=null){
//
//            curPageServiceProvideSearchInfo=pageServiceProvideSearchInfo;
//        }
//        if (spKind!=null){
//
//            curSpKind=spKind;
//        }
//        if (spSpecificKind!=null){
//
//            curSpSpecificKind=spSpecificKind;
//        }
//
//        if (curPageServiceProvideSearchInfo!=""   && curPageServiceProvideSearchInfo != null) {
//
//            searchMap.put("serviceProvideSearchInfo",curPageServiceProvideSearchInfo);
//            session.setAttribute("curServiceProvideSearchInfo",pageServiceProvideSearchInfo);
//        }else {
//
//            searchMap.put("serviceProvideSearchInfo",pageServiceProvideSearchInfo);
//        }
//        if (curSpKind!=null){
//
//            if (spKind==null){
//
//                spKind=INTEGER_NULL;
//            }
//            System.out.println("要放入到spkind"+curSpKind);
//            searchMap.put("spKind",curSpKind);
//            session.setAttribute("curSpKind",spKind);
//        }else {
//
//            searchMap.put("spKind",spKind);
//        }
//        if(curSpSpecificKind!=""&&  curSpSpecificKind!=null){
//
//            searchMap.put("spSpecificKind",curSpSpecificKind);
//            session.setAttribute("curSpSpecificKind",spSpecificKind);
//        }else {
//
//            searchMap.put("spSpecificKind",spSpecificKind);
//        }



        return "bft";
    }


    @RequestMapping("/applyServiceProvide/{spId}/{spCid}")
    public String applyServiceProvide (Model model,
                                       @PathVariable(value = "spId",required = false) Long spId,
                                       @PathVariable(value = "spCid",required = false) Long spCid,
                                       HttpSession session) throws ParseException {
        Map<String, Object> detectMap = new HashMap<String,Object>();
        detectMap.put("aspSpId",spId);
        detectMap.put("aspSid",Long.valueOf(session.getAttribute("currentStuId").toString()));
        detectMap.put("aspCid",spCid);
        ApplyServiceProvide repeatedInfoInApplyServiceProvide = applyServiceProvideService.isRepeatedInfoInApplyServiceProvide(detectMap);

        Util util = new Util();


        Map<String, Object> serviceProvide = serviceProvideService.fetchServiceProvideBySpId(spId);
        HashMap<String, Object> spKindAndSpId = new HashMap<>();
        spKindAndSpId.put("spId",spId);
        spKindAndSpId.put("spKind",Integer.valueOf(serviceProvide.get("spKind").toString()));

        List<Map<String, Object>> approximateServiceProvides = serviceProvideService.fetchApproximateServiceProvidesBySpKindAndSpId(spKindAndSpId);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);



        serviceProvide.put("spSubmitTime",util.computePageDays(serviceProvide.get("spSubmitTime").toString(),sdf));


        model.addAttribute("approximateServiceProvides",approximateServiceProvides);
        model.addAttribute("serviceProvide",serviceProvide);

        if (repeatedInfoInApplyServiceProvide!=null){
            model.addAttribute("apMsg","请勿重复申请同一个职位");
            return "serviceProvide";
        }
        ApplyServiceProvide applyServiceProvide=new ApplyServiceProvide();
        applyServiceProvide.setAspSpId(spId);
        applyServiceProvide.setAspSid(Long.valueOf(session.getAttribute("currentStuId").toString()));
        applyServiceProvide.setAspCid(spCid);
        applyServiceProvide.setAspSubmitTime(util.CSTDateFormatFromPageTransferToString(sdf.parse(sdf.format(System.currentTimeMillis())),sdf));
        applyServiceProvide.setAspStatus(UN_DISPOSED_STATUS);
        applyServiceProvideService.addApplyServiceProvide(applyServiceProvide);
        serviceProvideService.updateServiceProvideAppointmentBySpId(spId);
        model.addAttribute("apMsg","申请成功");

        return "serviceProvide";
    }

    @RequestMapping("/comFetchServiceProvidesList/{cId}/{pageNum}")
    public String comFetchServiceProvidesList(@RequestParam(required = false) String pageAddServiceProvideComSearchInfo,
                                              Model model,
                                              HttpSession session,
                                              @PathVariable(value = "cId")Long cId,
                                              @PathVariable(value = "pageNum",required = false) Integer pageNum){
        PageHelper.startPage(pageNum,3);
        if(session.getAttribute("curAddServiceProvideComSearchInfo")==null){
            session.setAttribute("curAddServiceProvideComSearchInfo",STRING_NULL);
        }
        String curAddServiceProvideComSearchInfo = session.getAttribute("curAddServiceProvideComSearchInfo").toString();
        Map<String,Object> searchInfo = new HashMap<String, Object>();
        List<Map<String, Object>> allServiceProvides=new ArrayList<Map<String,Object>>();
        searchInfo.put("spCid",cId);
        if (pageAddServiceProvideComSearchInfo!=null){
            curAddServiceProvideComSearchInfo=pageAddServiceProvideComSearchInfo;
            session.setAttribute("curAddServiceProvideComSearchInfo",pageAddServiceProvideComSearchInfo);
        }
        searchInfo.put("serviceProvideSearchInfo",curAddServiceProvideComSearchInfo);
        allServiceProvides=serviceProvideService.fetchAllServiceProvides(searchInfo);
        PageInfo<Map<String, Object>> allServiceProvidesPageInfo=new PageInfo<Map<String, Object>>(allServiceProvides);
        model.addAttribute("allServiceProvides",allServiceProvides);
        model.addAttribute("allServiceProvidesPageInfo",allServiceProvidesPageInfo);
        return "comAddServiceProvideList";
    }


    @RequestMapping("/comDeleteServiceProvideBySpId/{cId}/{pageNum}")
    public String comDeleteServiceProvideBySpId(@RequestParam(required = false) String pageAddServiceProvideComSearchInfo,
                                                Model model,
                                                HttpSession session,
                                                @RequestParam(required = false) Long pageSpId,
                                                @PathVariable(value = "cId")Long cId,
                                                @PathVariable(value = "pageNum",required = false) Integer pageNum){

        ServiceProvide updateServiceProvide = new ServiceProvide();
        updateServiceProvide.setSpId(pageSpId);
        updateServiceProvide.setSpSpcFlag(DELETED_FLAG);
        serviceProvideService.updateServiceProvide(updateServiceProvide);
        Map<String,Object> comDealMap = new HashMap<String,Object>();
        comDealMap.put("aspSpId",pageSpId);
        comDealMap.put("aspStatus",DEPRECATED_STATUS);
        applyServiceProvideService.comDealApplyServiceProvide(comDealMap);

        PageHelper.startPage(pageNum,3);
        if(session.getAttribute("curAddServiceProvideComSearchInfo")==null){
            session.setAttribute("curAddServiceProvideComSearchInfo",STRING_NULL);
        }
        String curAddServiceProvideComSearchInfo = session.getAttribute("curAddServiceProvideComSearchInfo").toString();
        Map<String,Object> searchInfo = new HashMap<String, Object>();
        List<Map<String, Object>> allServiceProvides=new ArrayList<Map<String,Object>>();
        searchInfo.put("spCid",cId);
        if (pageAddServiceProvideComSearchInfo!=null){
            curAddServiceProvideComSearchInfo=pageAddServiceProvideComSearchInfo;
            session.setAttribute("curAddServiceProvideComSearchInfo",pageAddServiceProvideComSearchInfo);
        }
        searchInfo.put("serviceProvideSearchInfo",curAddServiceProvideComSearchInfo);
        allServiceProvides=serviceProvideService.fetchAllServiceProvides(searchInfo);
        PageInfo<Map<String, Object>> allServiceProvidesPageInfo=new PageInfo<Map<String, Object>>(allServiceProvides);
        model.addAttribute("allServiceProvides",allServiceProvides);
        model.addAttribute("allServiceProvidesPageInfo",allServiceProvidesPageInfo);

        return "comAddServiceProvideList";
    }

    @RequestMapping("/comAddServiceProvide/{cId}")
    public String comAddServiceProvide(@RequestParam Map<String,Object> pageServiceProvide,
                                       @PathVariable(value = "cId")Long cId,
                                       HttpSession session,
                                       Model model) throws ParseException {
        Util util=new Util();
        int flag=util.isLegalInputServiceProvideMap(pageServiceProvide);
        if (flag==EMPTY_POJO){
            model.addAttribute("addMsg","请输入所有信息");
            return "comAddServiceProvide";
        }
        if (flag==ILLEGAL_INPUT_EXPENSE){
            model.addAttribute("addMsg","费用设置错误");
            return "comAddServiceProvide";
        }
        ServiceProvide addServiceProvide = util.getServiceProvideByPageParam(pageServiceProvide);
        addServiceProvide.setSpCid(cId);

        serviceProvideService.addServiceProvide(addServiceProvide);

        PageHelper.startPage(1,3);
        Map<String,Object> searchInfo = new HashMap<String, Object>();
        searchInfo.put("spCid",cId);
        if (session.getAttribute("curAddServiceProvideComSearchInfo")!=null){
            session.setAttribute("curAddServiceProvideComSearchInfo",STRING_NULL);
        }
        searchInfo.put("serviceProvideSearchInfo",session.getAttribute("curAddServiceProvideComSearchInfo").toString());
        List<Map<String, Object>> allServiceProvides=serviceProvideService.fetchAllServiceProvides(searchInfo);
        PageInfo<Map<String, Object>> allServiceProvidesPageInfo=new PageInfo<Map<String, Object>>(allServiceProvides);
        model.addAttribute("allServiceProvides",allServiceProvides);
        model.addAttribute("allServiceProvidesPageInfo",allServiceProvidesPageInfo);

        return "comAddServiceProvideList";
    }


    @RequestMapping("/comUpdateServiceProvide/{spId}")
    public String comUpdateServiceProvide(@RequestParam Map<String,Object> pageServiceProvide,
                                          @PathVariable(value = "spId")Long spId,
                                          Model model) throws ParseException {
        int flag=new Util().isLegalInputServiceProvideMap(pageServiceProvide);
        if (flag==EMPTY_POJO){
            Map<String, Object> serviceProvide = serviceProvideService.fetchServiceProvideBySpId(spId);
            model.addAttribute("serviceProvide",new Util().getPageServiceProvideJobFromMap(serviceProvide));
            model.addAttribute("updateMsg","请输入所有信息");
            return "comUpdateServiceProvide";
        }
        if (flag==ILLEGAL_INPUT_EXPENSE){
            Map<String, Object> serviceProvide = serviceProvideService.fetchServiceProvideBySpId(spId);
            model.addAttribute("serviceProvide",new Util().getPageServiceProvideJobFromMap(serviceProvide));
            model.addAttribute("updateMsg","费用设置错误");
            return "comUpdateServiceProvide";
        }
        pageServiceProvide.put("spId",spId);
        serviceProvideService.updateServiceProvide(new Util().getServiceProvideByPageParam(pageServiceProvide));

        Map<String, Object> serviceProvide = serviceProvideService.fetchServiceProvideBySpId(spId);
        model.addAttribute("serviceProvide",new Util().getPageServiceProvideJobFromMap(serviceProvide));
        model.addAttribute("updateMsg","更新成功");
        return "comUpdateServiceProvide";
    }

    @ResponseBody
    @RequestMapping("/comSelectServiceProvideKind")
    public List<String> comSelectServiceProvideKind(Integer spKind){
        List<String> specificKinds = serviceProvideService.fetchServiceProvideSpecificKindsByServiceProvideKind(spKind);
        System.out.println(specificKinds);
        return specificKinds;
    }


}
