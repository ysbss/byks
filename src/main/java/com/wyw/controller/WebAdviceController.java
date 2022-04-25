package com.wyw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.deploy.net.HttpResponse;
import com.wyw.pojo.WebAdvice;
import com.wyw.service.WebAdviceService;
import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Controller
@RequestMapping("/WebAdvice")
public class WebAdviceController {

    @Resource
    WebAdviceService webAdviceService;

    @RequestMapping(value = {"/fetchWebAdvicesList/{pageNum}/{waKind}","/fetchWebAdvicesList/{pageNum}"})
    public String fetchWebAdvicesList(Model model,
                                      HttpSession session,
                                      @PathVariable(value = "waKind",required = false) String pageWaKind,
                                      @PathVariable(value = "pageNum",required = false) Integer pageNum){

        System.out.println(pageWaKind);
        PageHelper.startPage(pageNum,3);
        String curWaKind=session.getAttribute("curWaKind").toString();
        if (pageWaKind !=null  ){
            curWaKind=pageWaKind;
            session.setAttribute("curWaKind",pageWaKind);
        }
        if (FinalStaticValue.INTEGER_NULL.toString().equals(pageWaKind)){
            System.out.println("我来了0");
            curWaKind=FinalStaticValue.STRING_NULL;
        }
        Map<String,Object> webAdvice=new HashMap<>();
        webAdvice.put("waKind",curWaKind);
        List<WebAdvice> allWebAdvices = webAdviceService.fetchWebAdvice(webAdvice);
        PageInfo<WebAdvice> allWebAdvicesPageInfo= new PageInfo<WebAdvice>(allWebAdvices);
        model.addAttribute("allWebAdvices",allWebAdvices);
        model.addAttribute("allWebAdvicesPageInfo",allWebAdvicesPageInfo);
        return "webAdvicesList";
    }

    @RequestMapping("/deleteWebAdvice/{pageNum}/{waId}")
    public String deleteWebAdvice(Model model,
                                  HttpSession session,
                                  @PathVariable(value = "waId") String waId,
                                  @PathVariable(value = "pageNum",required = false) Integer pageNum){

        Map<String,Object> updateWebAdvice = new HashMap<>();
        updateWebAdvice.put("waFlag",Long.valueOf(FinalStaticValue.DELETED_FLAG).toString() );
        updateWebAdvice.put("waId",waId);
        webAdviceService.updateWebAdvice(updateWebAdvice);


        PageHelper.startPage(pageNum,3);
        Map<String,Object> webAdvice=new HashMap<>();
        System.out.println(session.getAttribute("curWaKind").toString());
        webAdvice.put("waKind",session.getAttribute("curWaKind").toString());
        List<WebAdvice> allWebAdvices = webAdviceService.fetchWebAdvice(webAdvice);
        PageInfo<WebAdvice> allWebAdvicesPageInfo= new PageInfo<WebAdvice>(allWebAdvices);
        model.addAttribute("allWebAdvices",allWebAdvices);
        model.addAttribute("allWebAdvicesPageInfo",allWebAdvicesPageInfo);
        return "webAdvicesList";
    }

    @RequestMapping("/addWebService/{waSource}/{waSourceId}/{waSourceName}")
    public String addWebService(Model model,
                                HttpSession session,
                                @RequestParam Map<String,Object> pageWebAdvice,
                                @PathVariable(value = "waSource") String waSource,
                                @PathVariable(value = "waSourceId") String waSourceId,
                                @PathVariable(value = "waSourceName") String waSourceName) throws ParseException {
        System.out.println(pageWebAdvice.get("waKind"));
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        if (FinalStaticValue.EMPTY_POJO==new Util().isLegalInputWebServiceMap(pageWebAdvice)) {
            model.addAttribute("waMsg","请填写完整信息");
            return "bz";
        }

        pageWebAdvice.put("waSourceId",waSourceId);
        pageWebAdvice.put("waSourceName",waSourceName);
        System.out.println(waSource);
        switch (waSource){
            case FinalStaticValue.STR_STUDENT: pageWebAdvice.put("waSource",Long.valueOf(FinalStaticValue.INT_STUDENT).toString());break;
            case FinalStaticValue.STR_COMPANY: pageWebAdvice.put("waSource",Long.valueOf(FinalStaticValue.INT_COMPANY).toString());break;
            default:
        }
        pageWebAdvice.put("waKind",pageWebAdvice.get("waKind").toString().replaceAll("\'",""));
        pageWebAdvice.put("waSubmitTime",new  Util().CSTDateFormatFromPageTransferToString(sdf.parse(sdf.format(System.currentTimeMillis())),sdf));
        pageWebAdvice.put("waId",FinalStaticValue.STRING_NULL);

        pageWebAdvice.put("waSource",pageWebAdvice.get("waSource"));

        webAdviceService.addWebAdvice(pageWebAdvice);

        model.addAttribute("waMsg","提交意见成功");
        return "bz";
    }


}
