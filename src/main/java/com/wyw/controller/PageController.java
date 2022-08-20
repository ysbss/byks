package com.wyw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyw.pojo.*;
import com.wyw.service.*;
import com.wyw.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wyw.utils.FinalStaticValue.*;

/**
 * @author WYW
 */
@Controller
public class PageController {

    @Autowired
    @Qualifier("PartTimeJobServiceImpl")
    PartTimeJobService partTimeJobService;

    @Resource
    ApplyPartTimeJobService applyPartTimeJobService;

    @Resource
    StudentService studentService;

    @Resource
    FileCompanyService fileCompanyService;

    @Resource
    ServiceProvideService serviceProvideService;

    @Resource
    LocalInformationService localInformationService;

    @Resource
    ApplyServiceProvideService applyServiceProvideService;

    @Resource
    ApplyLocalInformationService applyLocalInformationService;

    @Resource
    WebAdviceService webAdviceService;

    @Resource
    FileResumeService fileResumeService;

    @Resource
    CompanyService companyService;

    @Resource
    SocketChatService socketChatService;


    @RequestMapping("/toWebServiceProtocolPage")
    public String toWebServiceProtocolPage(){

        return "webServiceProtocol";
    }



    @RequestMapping("/toBzPage")
    public String toBzPage(HttpSession session){
//        System.out.println(session.getAttribute("loginKind").toString());
        session.setAttribute("curApplyPartTimeJobComSearchInfo",STRING_NULL);
        session.setAttribute("curApplyPartTimeJobStuSearchInfo",STRING_NULL);


        session.setAttribute("curAddPartTimeJobComSearchInfo",STRING_NULL);
        session.setAttribute("curAddLocalInformationComSearchInfo",STRING_NULL);
        session.setAttribute("curAddServiceProvideComSearchInfo",STRING_NULL);

        return "bz";
    }
    @RequestMapping("/toBjPage")
    public String toBjPage(Model model,
                           @RequestParam(required = false) Integer lKind,
                           @RequestParam(required = false)String lSpecificKind,
                           HttpSession session) throws ParseException {
        Util util = new Util();
        List<Integer> localInformationKinds = localInformationService.fetchLocalInformationKinds();
        List<Map<String, Object>> localInformationSpecificKinds = localInformationService.fetchLocalInformationSpecificKinds();
        List<Map<String, Object>> homePageLocalInformation = localInformationService.fetchHomePageLocalInformation();

        HashMap<String, Object> localInformationSearchMap = new HashMap<String,Object>();
        session.setAttribute("curLocalInformationSearchInfo",STRING_NULL);
        session.setAttribute("curLKind",INTEGER_NULL);
        session.setAttribute("curLSpecificKind",STRING_NULL);




        session.setAttribute("curApplyPartTimeJobStuSearchInfo",STRING_NULL);
        session.setAttribute("curApplyPartTimeJobComSearchInfo",STRING_NULL);

        session.setAttribute("curAddPartTimeJobComSearchInfo",STRING_NULL);
        session.setAttribute("curAddLocalInformationComSearchInfo",STRING_NULL);
        session.setAttribute("curAddServiceProvideComSearchInfo",STRING_NULL);

        if(lKind!=null){
            //这个情况说明点击了大类或者点击了小类
            session.setAttribute("curLKind",lKind);
            localInformationSearchMap.put("lKind",lKind);
        }else {
            //说明是直接点击了首页
            session.setAttribute("curLKind",INTEGER_NULL);
            localInformationSearchMap.put("lKind",INTEGER_NULL);
        }
        if (!("".equals(lSpecificKind)) && lSpecificKind!= null ){
            //这个就确定了是点击了小类
            session.setAttribute("curLSpecificKind",lSpecificKind);
            localInformationSearchMap.put("lSpecificKind",lSpecificKind);
        }else {
            //这个就说明点的是大类
            session.setAttribute("curLSpecificKind",STRING_NULL);
            localInformationSearchMap.put("lSpecificKind",STRING_NULL);
        }


        localInformationSearchMap.put("localInformationSearchInfo",STRING_NULL);

        PageHelper.startPage(1,5);
        //分页启动的效果是作用在紧跟着的第一条sql语句
        List<Map<String, Object>> allLocalKindInformation = localInformationService.fetchAllLocalInformation(localInformationSearchMap);
        PageInfo<Map<String,Object>> allLocalKindInformationPageInfo=new PageInfo<Map<String,Object>>(allLocalKindInformation);


        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        for (Map m:homePageLocalInformation
        ) {
            m.put("spSubmitTime", util.StringFromDataBaseTransferToDate(m.get("lSubmitTime").toString(),sdf));
        }
        model.addAttribute("localInformationKinds",localInformationKinds);
        model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);

        model.addAttribute("homePageLocalInformation",homePageLocalInformation);
        model.addAttribute("allLocalKindInformation",allLocalKindInformation);

        model.addAttribute("allLocalKindInformationPageInfo",allLocalKindInformationPageInfo);


        return "bj";
    }

    @RequestMapping("/toBftPage")
    public String toBftPage(Model model,
                            @RequestParam(required = false) Integer spKind,
                            @RequestParam(required = false)String spSpecificKind,
                             HttpSession session) throws ParseException {


        Util util = new Util();
        List<Integer> serviceProvideKinds = serviceProvideService.fetchServiceProvideKinds();
        List<Map<String, Object>> serviceProvideSpecificKinds = serviceProvideService.fetchServiceProvideSpecificKinds();
        List<Map<String, Object>> homePageServiceProvides = serviceProvideService.fetchHomePageServiceProvides();

        HashMap<String, Object> serviceProvideSearchMap = new HashMap<String,Object>();
        session.setAttribute("curServiceProvideSearchInfo",STRING_NULL);
        session.setAttribute("curSpKind",INTEGER_NULL);
        session.setAttribute("curSpSpecificKind",STRING_NULL);



        session.setAttribute("curApplyPartTimeJobComSearchInfo",STRING_NULL);
        session.setAttribute("curApplyPartTimeJobStuSearchInfo",STRING_NULL);

        session.setAttribute("curAddPartTimeJobComSearchInfo",STRING_NULL);
        session.setAttribute("curAddLocalInformationComSearchInfo",STRING_NULL);
        session.setAttribute("curAddServiceProvideComSearchInfo",STRING_NULL);

        if(spKind!=null){
            //这个情况说明点击了大类或者点击了小类
            session.setAttribute("curSpKind",spKind);
            serviceProvideSearchMap.put("spKind",spKind);
        }else {
            //说明是直接点击了首页
            session.setAttribute("curSpKind",INTEGER_NULL);
            serviceProvideSearchMap.put("spKind",INTEGER_NULL);
        }
        if (!("".equals(spSpecificKind)) && spSpecificKind!= null ){
            //这个就确定了是点击了小类
            session.setAttribute("curSpSpecificKind",spSpecificKind);
            serviceProvideSearchMap.put("spSpecificKind",spSpecificKind);
        }else {
            //这个就说明点的是大类
            session.setAttribute("curSpSpecificKind",STRING_NULL);
            serviceProvideSearchMap.put("spSpecificKind",STRING_NULL);
        }


        serviceProvideSearchMap.put("serviceProvideSearchInfo",STRING_NULL);

        PageHelper.startPage(1,5);
        //分页启动的效果是作用在紧跟着的第一条sql语句
        List<Map<String, Object>> allServiceProvides = serviceProvideService.fetchAllServiceProvides(serviceProvideSearchMap);
        PageInfo<Map<String,Object>> allServiceProvidesPageInfo=new PageInfo<Map<String,Object>>(allServiceProvides);


        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        for (Map m:homePageServiceProvides
             ) {
            m.put("spSubmitTime", util.StringFromDataBaseTransferToDate(m.get("spSubmitTime").toString(),sdf));
        }
        model.addAttribute("serviceProvideKinds",serviceProvideKinds);
        model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);

        model.addAttribute("homePageServiceProvides",homePageServiceProvides);
        model.addAttribute("allServiceProvides",allServiceProvides);

        model.addAttribute("allServiceProvidesPageInfo",allServiceProvidesPageInfo);


        return "bft";
    }

    @RequestMapping("/toIndexPage")
    public String toIndexPage(Model model, HttpSession session,HttpServletRequest httpServletRequest) throws ParseException {



        session.setAttribute("curApplyPartTimeJobComSearchInfo",STRING_NULL);
        session.setAttribute("curApplyPartTimeJobStuSearchInfo",STRING_NULL);

        session.setAttribute("curAddPartTimeJobComSearchInfo",STRING_NULL);
        session.setAttribute("curAddLocalInformationComSearchInfo",STRING_NULL);
        session.setAttribute("curAddServiceProvideComSearchInfo",STRING_NULL);

        Util util = new Util();
        List<Map<String, Object>> allSpecialPartTimeJobs = partTimeJobService.getAllPageSpecialPartTimeJobs();

        List<Map<String, Object>> latestSpcPartTimeJobs = partTimeJobService.fetchLatestSpcPartTimeJob();


        List<Map<String,Object>> imgParTimeJobs= partTimeJobService.fetchImgParTimeJobs();

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
        for (Map m:imgParTimeJobs
        ) {
            m.put("pSubmitTime", util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
//            File file=new File(m.get("fFileStorePath").toString());
//            System.out.println(file.getAbsolutePath());
//            System.out.println(m.get("fFileStorePath"));
        }
        System.out.println(imgParTimeJobs.get(1).get("fFileStorePath"));
        model.addAttribute("testImgPath",imgParTimeJobs.get(1).get("fFileStorePath"));
        model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
        model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
        model.addAttribute("imgParTimeJobs",imgParTimeJobs);
        model.addAttribute("serviceProvideKinds",serviceProvideKinds);
        model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
        model.addAttribute("localInformationKinds",localInformationKinds);
        model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);

//        System.out.println(serviceProvideSpecificKinds.size());
//        System.out.println(serviceProvideSpecificKinds.get(0).get("spKind"));

        return "index";}

    @RequestMapping("/toZzyPage/{pId}")
    public String toZzyPage(@PathVariable(value = "pId",required = false) Long pId,
                            Model model) throws ParseException {
        Util util = new Util();
        Map<String, Object> partTimeJob = partTimeJobService.fetchSpcPartTimeJobByPid(pId);
        PartTimeJob updatePartTimeJob = new PartTimeJob();
        updatePartTimeJob.setPId(Long.valueOf(partTimeJob.get("pId").toString()));
        updatePartTimeJob.setPBrowseNum(Integer.parseInt(partTimeJob.get("pBrowseNum").toString()) + 1);
        partTimeJobService.updatePartTimeJob(updatePartTimeJob);
        partTimeJob= partTimeJobService.fetchSpcPartTimeJobByPid(pId);
        List<Map<String, Object>> approximatePartTimeJobs = partTimeJobService.fetchApproximatePartTimeJobByPid(pId);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        partTimeJob.put("pSubmitTime",util.computePageDays(partTimeJob.get("pSubmitTime").toString(),sdf));

        model.addAttribute("approximatePartTimeJobs",approximatePartTimeJobs);
        model.addAttribute("partTimeJob",partTimeJob);
        return "zzy";
    }


    @RequestMapping(value = {"/toStuInfoPage/{sId}","/toStuInfoPage/{sId}/{pId}"})
    public String toStuInfoPage(Model model,
                                @PathVariable(value = "sId") Long sId,
                                @PathVariable(value = "pId",required = false) Long pId    ){
        Map<String,Object> sIdAndFpId = new HashMap<String,Object>();
        if (pId==null){
            pId=null;
        }
        sIdAndFpId.put("sId",sId);
        sIdAndFpId.put("fFilePid",pId);
        Map<String, Object> studentInfo = studentService.fetchStuWithResumeById(sIdAndFpId);
        System.out.println(studentInfo.get("sName"));
        System.out.println(studentInfo.get("sEmail"));

        model.addAttribute("stuInfo",studentInfo);
        model.addAttribute("tmpPid",pId);
        return "studentSelfInfo";
    }



    @RequestMapping("/toBsPage")
    public String toBsPage(Model model,HttpSession session) throws ParseException {
        Util util = new Util();
        List<Map<String, Object>> allSpecialPartTimeJobs = partTimeJobService.getAllPageSpecialPartTimeJobs();
        List<Map<String, Object>> latestSpcPartTimeJobs = partTimeJobService.fetchLatestSpcPartTimeJob();

        List<Integer> serviceProvideKinds = serviceProvideService.fetchServiceProvideKinds();
        List<Map<String, Object>> serviceProvideSpecificKinds = serviceProvideService.fetchServiceProvideSpecificKinds();

        List<Integer> localInformationKinds = localInformationService.fetchLocalInformationKinds();
        List<Map<String, Object>> localInformationSpecificKinds = localInformationService.fetchLocalInformationSpecificKinds();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        for (Map m:allSpecialPartTimeJobs
        ) {
            m.put("pSubmitTime",util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
        }for (Map m:latestSpcPartTimeJobs
        ) {
            m.put("pSubmitTime", util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
        }

        session.setAttribute("curApplyPartTimeJobComSearchInfo",STRING_NULL);
        session.setAttribute("curApplyPartTimeJobStuSearchInfo",STRING_NULL);


        session.setAttribute("curAddPartTimeJobComSearchInfo",STRING_NULL);
        session.setAttribute("curAddLocalInformationComSearchInfo",STRING_NULL);
        session.setAttribute("curAddServiceProvideComSearchInfo",STRING_NULL);

        model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
        model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
        model.addAttribute("serviceProvideKinds",serviceProvideKinds);
        model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
        model.addAttribute("localInformationKinds",localInformationKinds);
        model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);

        return "index_1";
    }

    @RequestMapping("/toBftMorePage")
    public String toBftMorePage(Model model,
                                HttpSession session
                                ){


        PageHelper.startPage(1,5);
        session.setAttribute("partTimeJobSearchInfo",STRING_NULL);
        List<Map<String, Object>> allPartTimeJobs = partTimeJobService.getAllPartTimeJobs(STRING_NULL);
        System.out.println(allPartTimeJobs.size());
        PageInfo<Map<String, Object>> allPartTimeJobsPageInfo = new PageInfo<Map<String, Object>>(allPartTimeJobs);


        model.addAttribute("allPartTimeJobs",allPartTimeJobs);

        model.addAttribute("allPartTimeJobsPageInfo",allPartTimeJobsPageInfo);
        System.out.println(allPartTimeJobsPageInfo.getPageSize());
        return "bft_more";
    }
    @RequestMapping("/toLocalInformationPage/{lId}")
    public String toLocalInformationPage (Model model,
                                          @PathVariable(value = "lId")Long lId) throws ParseException {
        Util util = new Util();
        localInformationService.updateLocalInformationBrowsePersonByLid(lId);

        Map<String, Object> localInformation = localInformationService.fetchLocalInformationBylId(lId);

        Map<String,Object> lKindAndLid=new HashMap<String,Object>();

        lKindAndLid.put("lId",lId);
        lKindAndLid.put("lKind",Integer.valueOf(localInformation.get("lKind").toString()));

        List<Map<String, Object>> approximateLocalInformation = localInformationService.fetchApproximateLocalInformationBylKindAndLid(lKindAndLid);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        localInformation.put("lSubmitTime",util.computePageDays(localInformation.get("lSubmitTime").toString(),sdf));


        model.addAttribute("approximateLocalInformation",approximateLocalInformation);
        model.addAttribute("localInformation",localInformation);

        return "localInformation";
    }

    @RequestMapping("/toServiceProvidePage/{spId}")
    public String toServiceProvidePage(Model model,
                                       @PathVariable(value = "spId") Long spId) throws ParseException {

        Util util = new Util();
        serviceProvideService.updateServiceProvideBrowsePersonBySpId(spId);

        Map<String, Object> serviceProvide = serviceProvideService.fetchServiceProvideBySpId(spId);
        HashMap<String, Object> spKindAndSpId = new HashMap<>();
        spKindAndSpId.put("spId",spId);
        spKindAndSpId.put("spKind",Integer.valueOf(serviceProvide.get("spKind").toString()));

        List<Map<String, Object>> approximateServiceProvides = serviceProvideService.fetchApproximateServiceProvidesBySpKindAndSpId(spKindAndSpId);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);



        serviceProvide.put("spSubmitTime",util.computePageDays(serviceProvide.get("spSubmitTime").toString(),sdf));


        model.addAttribute("approximateServiceProvides",approximateServiceProvides);
        model.addAttribute("serviceProvide",serviceProvide);

        return "serviceProvide";
    }




    @RequestMapping("/toStuApplyPartTimeJob/{sId}")
    public String toStuApplyPartTimeJob(Model model,
                                        HttpSession session,
                                        @PathVariable(value = "sId") Long sId){
        PageHelper.startPage(1,3);
        Map<String, Object> toPageMap = new HashMap<String, Object>();
        toPageMap.put("apSid",sId);
        toPageMap.put("applyPartTimeJobSearchInfo",session.getAttribute("curApplyPartTimeJobStuSearchInfo").toString());
        List<Map<String, Object>> allApplyPartTimeJobs = applyPartTimeJobService.fetchApplyPartTimeJobsBysIdAndSearchInfo(toPageMap);
        PageInfo<Map<String, Object>> allApplyPartTimeJobsPageInfo = new PageInfo<Map<String, Object>>(allApplyPartTimeJobs);

        model.addAttribute("allApplyPartTimeJobs",allApplyPartTimeJobs);
        model.addAttribute("allApplyPartTimeJobsPageInfo",allApplyPartTimeJobsPageInfo);
        return "stuApplyPartTimeJobList";
    }




    @RequestMapping("/toComApplyPartTimeJob/{cId}")
    public String toComApplyPartTimeJob(Model model,
                                        HttpSession session,
                                        @PathVariable(value = "cId") Long cId){
        PageHelper.startPage(1,3);
        Map<String, Object> toPageMap = new HashMap<String, Object>();
        toPageMap.put("apCid",cId);
        toPageMap.put("applyPartTimeJobSearchInfo",session.getAttribute("curApplyPartTimeJobComSearchInfo").toString());
        List<Map<String, Object>> allApplyPartTimeJobs = applyPartTimeJobService.fetchApplyPartTimeJobsBycIdAndSearchInfo(toPageMap);
        PageInfo<Map<String, Object>> allApplyPartTimeJobsPageInfo = new PageInfo<Map<String, Object>>(allApplyPartTimeJobs);

        model.addAttribute("allApplyPartTimeJobs",allApplyPartTimeJobs);
        model.addAttribute("allApplyPartTimeJobsPageInfo",allApplyPartTimeJobsPageInfo);
        return "comApplyPartTimeJobList";
    }

    @RequestMapping("/toStuApplyServiceProvide/{sId}")
    public String toStuApplyServiceProvide(Model model,
                                           HttpSession session,
                                           @PathVariable(value = "sId") Long sId){
        PageHelper.startPage(1,3);
        session.setAttribute("curApplyServiceProvideStuSearchInfo",STRING_NULL);
        Map<String, Object> toPageMap = new HashMap<String, Object>();
        toPageMap.put("aspSid",sId);
        toPageMap.put("applyServiceProvidesSearchInfo",STRING_NULL);
        List<Map<String,Object>> allApplyServiceProvides=applyServiceProvideService.fetchApplyServiceProvidesBysIdAndSearchInfo(toPageMap);
        PageInfo<Map<String, Object>> allApplyServiceProvidesPageInfo = new PageInfo<Map<String, Object>>(allApplyServiceProvides);
        model.addAttribute("allApplyServiceProvides",allApplyServiceProvides);
        model.addAttribute("allApplyServiceProvidesPageInfo",allApplyServiceProvidesPageInfo);
        return "stuApplyServiceProvideList";
    }


    @RequestMapping("/toComApplyServiceProvide/{cId}")
    public String toComApplyServiceProvide(Model model,
                                      HttpSession session,
                                      @PathVariable(value = "cId") Long cId){
        PageHelper.startPage(1,3);
        session.setAttribute("curApplyServiceProvideComSearchInfo",STRING_NULL);
        Map<String, Object> toPageMap = new HashMap<String, Object>();
        toPageMap.put("aspCid",cId);
        toPageMap.put("applyServiceProvidesSearchInfo",STRING_NULL);
        List<Map<String,Object>> allApplyServiceProvides=applyServiceProvideService.fetchApplyServiceProvidesBycIdAndSearchInfo(toPageMap);
        PageInfo<Map<String, Object>> allApplyServiceProvidesPageInfo = new PageInfo<Map<String, Object>>(allApplyServiceProvides);

        model.addAttribute("allApplyServiceProvides",allApplyServiceProvides);
        model.addAttribute("allApplyServiceProvidesPageInfo",allApplyServiceProvidesPageInfo);

        return "comApplyServiceProvideList";
    }



    @RequestMapping("/toStuApplyLocalInformation/{sId}")
    public String toStuApplyLocalInformation(Model model,
                                             HttpSession session,
                                             @PathVariable(value = "sId") Long sId){
        PageHelper.startPage(1,3);
        session.setAttribute("curApplyLocalInformationStuSearchInfo",STRING_NULL);
        Map<String, Object> toPageMap = new HashMap<String, Object>();
        toPageMap.put("aliSid",sId);
        toPageMap.put("applyLocalInformationSearchInfo",STRING_NULL);
        List<Map<String, Object>> allApplyLocalInformation = applyLocalInformationService.fetchApplyLocalInformationBysIdAndSearchInfo(toPageMap);
        PageInfo<Map<String, Object>> allApplyLocalInformationPageInfo = new PageInfo<Map<String, Object>>(allApplyLocalInformation);
        model.addAttribute("allApplyLocalInformation",allApplyLocalInformation);
        model.addAttribute("allApplyLocalInformationPageInfo",allApplyLocalInformationPageInfo);
        return "stuApplyLocalInformationList";
    }

    @RequestMapping("/toComApplyLocalInformation/{cId}")
    public String toComApplyLocalInformation(Model model,
                                             HttpSession session,
                                             @PathVariable(value = "cId") Long cId){
        PageHelper.startPage(1,3);
        session.setAttribute("curApplyLocalInformationComSearchInfo",STRING_NULL);
        Map<String, Object> toPageMap = new HashMap<String, Object>();
        toPageMap.put("aliCid",cId);
        toPageMap.put("applyLocalInformationSearchInfo",STRING_NULL);
        List<Map<String, Object>> allApplyLocalInformation = applyLocalInformationService.fetchApplyLocalInformationBycIdAndSearchInfo(toPageMap);
        PageInfo<Map<String, Object>> allApplyLocalInformationPageInfo = new PageInfo<Map<String, Object>>(allApplyLocalInformation);
        model.addAttribute("allApplyLocalInformation",allApplyLocalInformation);
        model.addAttribute("allApplyLocalInformationPageInfo",allApplyLocalInformationPageInfo);
        return "comApplyLocalInformationList";
    }


    @RequestMapping("/toComAddPartTimeJobList/{cId}")
    public String toComAddPartTimeJobList(Model model,
                                      HttpSession session,
                                      @PathVariable(value = "cId") Long cId){
        PageHelper.startPage(1,3);

        Map<String, Object> cIdAndSearchInfo = new HashMap<String, Object>();
        cIdAndSearchInfo.put("cId",cId);


        cIdAndSearchInfo.put("partTimeJobSearchInfo",session.getAttribute("curAddPartTimeJobComSearchInfo").toString());

        List<Map<String, Object>> allPartTimeJobsByCidAndSearchInfo = partTimeJobService.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo);
        PageInfo<Map<String, Object>> allPartTimeJobsByCidAndSearchInfoPageInfo = new PageInfo<Map<String, Object>>(allPartTimeJobsByCidAndSearchInfo);
        model.addAttribute("allPartTimeJobsByCidAndSearchInfo",allPartTimeJobsByCidAndSearchInfo);
        model.addAttribute("allPartTimeJobsByCidAndSearchInfoPageInfo",allPartTimeJobsByCidAndSearchInfoPageInfo);
        return "comAddPartTimeJobList";
    }

    @RequestMapping("/toAddPartTimeJob")
    public String toAddPartTimeJob(HttpSession session){
        System.out.println(session.getAttribute("currentComId"));

        return "comAddPartTimeJob";
    }

    @RequestMapping("/toUpdatePartTimeJob/{pId}")
    public String toUpdatePartTimeJob(@PathVariable(value = "pId")Long pId,
                                      Model model){

        Util util=new Util();
        Map<String, Object> pagePartTimeJob = partTimeJobService.fetchSpcPartTimeJobByPid(pId);
        pagePartTimeJob=util.getPagePartTimeJobFromMap(pagePartTimeJob);
        model.addAttribute("partTimeJob",pagePartTimeJob);
        return "comUpdatePartTimeJob";
    }

    @RequestMapping("/toComAddLocalInformationList/{cId}")
    public String toComAddLocalInformationList(Model model,
                                           HttpSession session,
                                           @PathVariable(value = "cId") Long cId){
        PageHelper.startPage(1,3);
        Map<String,Object> searchInfo=new HashMap<String, Object>();
        searchInfo.put("lCid",cId);
        searchInfo.put("localInformationSearchInfo",session.getAttribute("curAddLocalInformationComSearchInfo").toString());
        List<Map<String, Object>> allLocalInformation = localInformationService.fetchAllLocalInformation(searchInfo);
        PageInfo<Map<String, Object>> allLocalInformationPageInfo=new PageInfo<Map<String, Object>>(allLocalInformation);
        model.addAttribute("allLocalInformation",allLocalInformation);
        model.addAttribute("allLocalInformationPageInfo",allLocalInformationPageInfo);
        return "comAddLocalInformationList";
    }

    @RequestMapping("/toComAddLocalInformation")
    public String toComAddLocalInformation(){
        return "comAddLocalInformation";
    }

    @RequestMapping("/toComUpdateLocalInformation/{lId}")
    public String toComUpdateLocalInformation(@PathVariable(value = "lId")Long lId,
                                              Model model){



        model.addAttribute("localInformation",localInformationService.fetchLocalInformationBylId(lId));
        model.addAttribute("defaultSpecificKinds",localInformationService.fetchLocalInformationSpecificKindsByLocalInformationKind(Integer.valueOf(localInformationService.fetchLocalInformationBylId(lId).get("lKind").toString())));
        return "comUpdateLocalInformation";
    }


    @RequestMapping("/toComAddServiceProvideList/{cId}")
    public String toComAddServiceProvideList(Model model,
                                             HttpSession session,
                                             @PathVariable(value = "cId") Long cId){
        PageHelper.startPage(1,3);
        Map<String,Object> searchInfo=new HashMap<String, Object>();
        searchInfo.put("spCid",cId);
        searchInfo.put("serviceProvideSearchInfo",session.getAttribute("curAddServiceProvideComSearchInfo").toString());
        List<Map<String, Object>> allServiceProvides = serviceProvideService.fetchAllServiceProvides(searchInfo);
        PageInfo<Map<String, Object>> allServiceProvidesPageInfo=new PageInfo<Map<String, Object>>(allServiceProvides);
        model.addAttribute("allServiceProvides",allServiceProvides);
        model.addAttribute("allServiceProvidesPageInfo",allServiceProvidesPageInfo);

        return "comAddServiceProvideList";
    }

    @RequestMapping("/toComAddServiceProvide")
    public String toComAddServiceProvide(){return "comAddServiceProvide";}

    @RequestMapping("/toComUpdateServiceProvide/{spId}")
    public String toComUpdateServiceProvide(@PathVariable(value = "spId")Long spId,
                                            Model model){

        model.addAttribute("serviceProvide",new Util().getPageServiceProvideJobFromMap(serviceProvideService.fetchServiceProvideBySpId(spId)));
        model.addAttribute("defaultSpecificKinds",serviceProvideService.fetchServiceProvideSpecificKindsByServiceProvideKind(Integer.valueOf(serviceProvideService.fetchServiceProvideBySpId(spId).get("spKind").toString())));
        return "comUpdateServiceProvide";
    }

    @RequestMapping("/toWebAdvicesList")
    public String toWebAdvicesList(HttpSession session,
                              Model model){
        session.setAttribute("curWaKind",STRING_NULL);
        PageHelper.startPage(1,3);
        List<WebAdvice> allWebAdvices = webAdviceService.fetchWebAdvice(new HashMap<>());
        PageInfo<WebAdvice> allWebAdvicesPageInfo= new PageInfo<WebAdvice>(allWebAdvices);
        model.addAttribute("allWebAdvices",allWebAdvices);
        model.addAttribute("allWebAdvicesPageInfo",allWebAdvicesPageInfo);
        return "webAdvicesList";
    }

    @RequestMapping("/toStuAdd")
    public String toStuAdd(){
        return "registerStu";
    }
    @RequestMapping("/toStuForgetPassword")
    public String toStuForgetPassword(){

        return "stuForgetPassword";
    }
    @RequestMapping("/toAllStusList")
    public String toAllStusList(Model model,
                                HttpSession session){
        session.setAttribute("curStuSearchInfo",STRING_NULL);
        PageHelper.startPage(1,3);
        List<Student> allStusList = studentService.fetchStusList(new HashMap<>());
        PageInfo<Student> allStusListPageInfo=new  PageInfo<Student>(allStusList);
        model.addAttribute("allStusList",allStusList);
        model.addAttribute("allStusListPageInfo",allStusListPageInfo);
        return "allStusList";
    }

    @RequestMapping("/toStuUpdate/{sId}")
    public String toStuUpdate(Model model,
                              @PathVariable(value = "sId") Long sId){


            Student studentInfo = studentService.fetchStuById(sId);

        Map<String,Object> fileResume=new HashMap<String,Object>();

        fileResume.put("fFileSid",sId);


        model.addAttribute("stuInfo",studentInfo);
        model.addAttribute("fileResumes",fileResumeService.fetchFileResumesList(fileResume));

        return "stuUpdate";
    }

    @RequestMapping("/toStuRevisePassword/{sId}")
    public String toStuRevisePassword( @PathVariable(value = "sId") Long sId,
                                       Model model){

        model.addAttribute("oldPassword",studentService.fetchStuById(sId).getSPassword());
        return "stuRevisePassword";
    }


    @RequestMapping("/toComAdd")
    public String toComAdd(){
        return "registerCom";
    }
    @RequestMapping("/toComForgetPassword")
    public String toComForgetPassword(){

        return "comForgetPassword";
    }

    @RequestMapping("/toAllCompaniesList")
    public String toAllCompaniesList(Model model,
                                     HttpSession session){
        session.setAttribute("curComSearchInfo",STRING_NULL);
        PageHelper.startPage(1,3);
        List<Company> allCompaniesList = companyService.fetchCompaniesList(new HashMap<String, Object>());
        PageInfo<Company> allCompaniesListPageInfo=new  PageInfo<Company>(allCompaniesList);
        model.addAttribute("allCompaniesList",allCompaniesList);
        model.addAttribute("allCompaniesListPageInfo",allCompaniesListPageInfo);

        return "allCompaniesList";
    }

    @RequestMapping("/toComUpdate/{cId}")
    public String toComUpdate(@PathVariable(value = "cId") Long cId,
                              Model model){
        Map<String, Object> fileCompany = new HashMap<>();
        fileCompany.put("fFileCid",cId);
        model.addAttribute("comInfo",companyService.fetchCompanyByCid(cId));
        model.addAttribute("comImgPath",fileCompanyService.fetchFileCompanyList(fileCompany).get(0).getFFileStorePath());
        return "comUpdate";
    }


    @RequestMapping("/toComRevisePassword/{cId}")
    public String toComRevisePassword( @PathVariable(value = "cId") Long cId,
                                       Model model){

        model.addAttribute("oldPassword",companyService.fetchCompanyByCid(cId).getCPassword());
        return "comRevisePassword";
    }


    @RequestMapping("/toAdminAdd")
    public String toAdminAdd(){
        return "registerAdmin";
    }
    @RequestMapping("/toAdminForgetPassword")
    public String toAdminForgetPassword(){

        return "adminForgetPassword";
    }






    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session,Model model) throws ParseException {
        session.invalidate();
        System.out.println("我退出");
        Util util = new Util();
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
        }for (Map m:latestSpcPartTimeJobs
        ) {
            m.put("pSubmitTime", util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
        }
        model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
        model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
        model.addAttribute("serviceProvideKinds",serviceProvideKinds);
        model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
        model.addAttribute("localInformationKinds",localInformationKinds);
        model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);


        return "index";

    }



    @RequestMapping("/toLoginPage")
    public String toLoginPage(){

        return "dl";
    }


    @RequestMapping("/stuToZzyChatRoom/{cId}")
    public String stuToZzyChatRoom(@PathVariable(value = "cId") Long cId,Model model, HttpSession session){
        Map<String,Object> queryMap=new HashMap<String,Object>();
//        if (session.getAttribute("currentStuId")!=null){
//            queryMap.put("scSendId",session.getAttribute("currentStuId").toString());
//        }
//        if (session.getAttribute("currentComId")!=null){
//            queryMap.put("scSendId",session.getAttribute("currentComId").toString());
//        }
        queryMap.put("scSendId",Long.valueOf(session.getAttribute("currentChatId").toString()));
        queryMap.put("scReceiveId",cId);
        model.addAttribute("receiveId",cId);
        model.addAttribute("resultChatList",socketChatService.querySocketChatList(queryMap));

        return "ChatRoom";
    }

    @RequestMapping("/toChatNews/{scReceiveId}")
    public String toChatNews (@PathVariable(value = "scReceiveId") Long scReceiveId,Model model,
                              HttpSession session){
        PageHelper.startPage(1,3);

        List<SocketChat> queryChatNews = socketChatService.queryChatNews(scReceiveId);
        PageInfo<SocketChat> pageQueryChatNews=new PageInfo<SocketChat>(queryChatNews);
        model.addAttribute("queryChatNews",queryChatNews);
        model.addAttribute("pageQueryChatNews",pageQueryChatNews);
        return "chatNews";
    }



}
