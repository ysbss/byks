package com.wyw.config;

import com.wyw.service.LocalInformationService;
import com.wyw.service.PartTimeJobService;
import com.wyw.service.ServiceProvideService;
import com.wyw.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object currentName = request.getSession().getAttribute("currentName");

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
        }



        if (currentName==null){
            request.setAttribute("lMsg","无权限访问");
            request.setAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
            request.setAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
            request.setAttribute("imgParTimeJobs",imgParTimeJobs);
            request.setAttribute("serviceProvideKinds",serviceProvideKinds);
            request.setAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
            request.setAttribute("localInformationKinds",localInformationKinds);
            System.out.println(localInformationKinds.size());
            System.out.println(localInformationSpecificKinds.size());
            request.setAttribute("localInformationSpecificKinds",localInformationSpecificKinds);

            request.getRequestDispatcher("/index.html").forward(request,response);


            return false;
            //如果因为时间过长session自动注销，那么跳转的首页不能正常显示数据，因为这里没给首页数据，网上说对拦截器配置进行修改
        }else {
            return true;
        }
    }


}
