package com.wyw.controller;

import com.wyw.service.PartTimeJobService;
import com.wyw.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/HomePage")
public class HomePageControllerImpl {

    @Resource
    PartTimeJobService partTimeJobService;

    @RequestMapping("/indexDataInit")
    public String indexDataInit(Model model) throws ParseException {
        Util util = new Util();
        System.out.println("我进来了");
        List<Map<String, Object>> allSpecialPartTimeJobs = partTimeJobService.getAllPageSpecialPartTimeJobs();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        for (Map m:allSpecialPartTimeJobs
        ) {
            m.put("pSubmitTime", util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
            System.out.println(m.get("pName"));
        }

        model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
        return "index";
    }
}
