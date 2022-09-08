package com.wyw.config;

import com.wyw.service.LocalInformationService;
import com.wyw.service.PartTimeJobService;
import com.wyw.service.ServiceProvideService;
import com.wyw.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author WYW
 */

public class LoginHandlerInterceptor  implements HandlerInterceptor {

    @Resource
    PartTimeJobService partTimeJobService;

    @Resource
    ServiceProvideService serviceProvideService;

    @Resource
    LocalInformationService localInformationService;

//    经过测试每次请求都会经过这个
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        //该字段可选，是个布尔值，表示是否可以携带cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT,OPTIONS");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())){
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
        //加上了上面就可以保证跨域~~
        Object currentName = request.getSession().getAttribute("currentName");

//return true;
//        如果要进行测试跨域ajax请求，必须注释掉下面的代码直接return true，不然由于进行了网页跳转，会将网页源代码作为响应体返回，导致前端呈现网页源代码
        //在前端设置了x-requested-with，并且在上面设置了允许setHeader，在这里判断是否为ajax请求就可以正常判断了。
       System.out.println("X-Requested-With:"+request.getHeader("X-Requested-With"));
//不判断是否为ajax请求的话，会直接跳到下面的if判断，又因为进行了网页跳转，最后会将页面源代码呈现回去
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            System.out.println("我来判断X-Requested-With");
            return true;
        }
        if (currentName==null){
//            Util util = new Util();
//
//            List<Map<String, Object>> allSpecialPartTimeJobs = partTimeJobService.getAllPageSpecialPartTimeJobs();
//
//
//            List<Map<String, Object>> latestSpcPartTimeJobs = partTimeJobService.fetchLatestSpcPartTimeJob();
//
//            List<Map<String,Object>> imgParTimeJobs= partTimeJobService.fetchImgParTimeJobs();
//
//            List<Integer> serviceProvideKinds = serviceProvideService.fetchServiceProvideKinds();
//            List<Map<String, Object>> serviceProvideSpecificKinds = serviceProvideService.fetchServiceProvideSpecificKinds();
//
//            List<Integer> localInformationKinds = localInformationService.fetchLocalInformationKinds();
//            List<Map<String, Object>> localInformationSpecificKinds = localInformationService.fetchLocalInformationSpecificKinds();
//            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//            for (Map m:allSpecialPartTimeJobs
//            ) {
//                m.put("pSubmitTime", util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
//            }
//            for (Map m:latestSpcPartTimeJobs
//            ) {
//                m.put("pSubmitTime", util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
//            }
//            for (Map m:imgParTimeJobs
//            ) {
//                m.put("pSubmitTime", util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
//            }
//            request.setAttribute("lMsg","无权限访问");
//            request.setAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
//            request.setAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
//            request.setAttribute("imgParTimeJobs",imgParTimeJobs);
//            request.setAttribute("serviceProvideKinds",serviceProvideKinds);
//            request.setAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
//            request.setAttribute("localInformationKinds",localInformationKinds);
//            System.out.println(localInformationKinds.size());
//            System.out.println(localInformationSpecificKinds.size());
//            request.setAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
//            request.getRequestDispatcher("/index.html").forward(request,response);
            request.getRequestDispatcher("/").forward(request,response);
            return false;
            //如果因为时间过长session自动注销，那么跳转的首页不能正常显示数据，因为这里没给首页数据，网上说对拦截器配置进行修改
        }else {
            return true;
        }
    }


}
