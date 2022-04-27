package com.wyw.controller;


import com.wyw.pojo.Admin;
import com.wyw.service.AdminService;
import com.wyw.service.LocalInformationService;
import com.wyw.service.PartTimeJobService;
import com.wyw.service.ServiceProvideService;
import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.RandomStr;
import com.wyw.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.wyw.utils.FinalStaticValue.*;

/**
 * @author WYW
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    @Qualifier("AdminServiceImpl")
    AdminService adminService;

    @Autowired
    @Qualifier("PartTimeJobServiceImpl")
    PartTimeJobService partTimeJobService;

    @Resource
    ServiceProvideService serviceProvideService;

    @Resource
    LocalInformationService localInformationService;

    @Resource
    JavaMailSenderImpl javaMailSender;

    @Resource
    Util util;

    @Resource
    RandomStr randomStr;


    @RequestMapping("/adminLogin")
    public String adminLogin(@RequestParam(value = "aId",required = false) Long aId,
                             @RequestParam(value = "aPassword",required = false) String aPassword,
                             HttpSession session,
                             Model model) throws ParseException {
        Admin admin = adminService.fetchAdminById(aId);
        Util util = new Util();
        int legalInputLoginFlag = util.isLegalInputLogin(aId,aPassword,admin,model);
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
        }
        for (Map m:latestSpcPartTimeJobs
        ) {
            m.put("pSubmitTime", util.StringFromDataBaseTransferToDate(m.get("pSubmitTime").toString(),sdf));
        }

        switch (legalInputLoginFlag){
            case FinalStaticValue.EMPTY_ACCOUNT:{
                System.out.println("管理员账号空");
                model.addAttribute("amsg","输入的用户名为空");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
                return "index";
            }
            case FinalStaticValue.EMPTY_PASSWORD:{
                System.out.println("管理员密码空");
                model.addAttribute("amsg","输入的密码为空");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
                return "index";
            }
            case FinalStaticValue.EMPTY_USER:{
                System.out.println("管理员没有此用户");
                model.addAttribute("amsg","没有此用户");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
                return "index";
            }
            case FinalStaticValue.ERROR_PASSWORD:{

                System.out.println("管理员密码错");
                model.addAttribute("amsg","输入的密码错误");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);
                return "index";
            }
            default:{
                session.setAttribute("currentName",admin.getAName());
                System.out.println("我进来了管理员");
                model.addAttribute("spcPartTimeJobs",allSpecialPartTimeJobs);
                model.addAttribute("latestSpcPartTimeJobs",latestSpcPartTimeJobs);
                model.addAttribute("loginKind","管理员");
                model.addAttribute("serviceProvideKinds",serviceProvideKinds);
                model.addAttribute("serviceProvideSpecificKinds",serviceProvideSpecificKinds);
                model.addAttribute("localInformationKinds",localInformationKinds);
                model.addAttribute("localInformationSpecificKinds",localInformationSpecificKinds);


                session.setAttribute("loginKind","管理员");
                return "index_1";
            }
        }
    }

    @RequestMapping("/addAdmin")
    public String addAdmin(@RequestParam Map<String,Object> pageAdmin,
                           HttpSession session,
                           Model model){

        if (session.getAttribute("curAdminRegisterCheckCode")==null) {
            session.setAttribute("curAdminRegisterCheckCode",STRING_NULL);
        }

        switch (util.isLegalInputAdminMap(pageAdmin,session.getAttribute("curAdminRegisterCheckCode").toString(),LONG_NULL)) {
            case EMPTY_POJO:{model.addAttribute("registerMsg","请填写完整信息");return "registerAdmin";}
            case ERROR_EMAIL:{model.addAttribute("registerMsg","错误的邮箱");return "registerAdmin";}
            case REPEATED_EMAIL:{model.addAttribute("registerMsg","该邮箱已被注册");return "registerAdmin";}
            case ERROR_CHECK_CODE:{model.addAttribute("registerMsg","不正确的密钥");return "registerAdmin";}
            case SUCCESS:break;
            default:
        }

        String password=randomStr.getRandomString(6);
        pageAdmin.put("aId",INTEGER_NULL);
        pageAdmin.put("aPassword",password);
        adminService.addAdmin(pageAdmin);

        util.sendMail(ADMIN_REGISTER_SUBJECT,ADMIN_REGISTER_TEXT_ACCOUNT+pageAdmin.get("aId").toString()+ADMIN_REGISTER_TEXT_PASSWORD+password,DEFAULT_MAIL_SENDER,pageAdmin.get("aEmail").toString());

        model.addAttribute("registerMsg","注册成功");
        return "registerAdmin";
    }

    @RequestMapping("/getAdminRandomRegisterCheckCode")
    public String getAdminRegisterRandomCheckCode(HttpSession session){

        System.out.println(randomStr.getRandomString(6));
        session.setAttribute("curAdminRegisterCheckCode",randomStr.getRandomString(6));
        System.out.println(session.getAttribute("curAdminRegisterCheckCode").toString());
        return "registerAdmin";
    }
    @RequestMapping("/getAdminForgerPasswordRandomCheckCode")
    public String getAdminForgerPasswordRandomCheckCode(HttpSession session){

        session.setAttribute("curAdminForgetPasswordCheckCode",randomStr.getRandomString(6));
        System.out.println(session.getAttribute("curAdminForgetPasswordCheckCode").toString());
        return "adminForgetPassword";
    }



    @RequestMapping("/adminFetchPasswordByEmail")
    public String adminFetchPasswordByEmail(@RequestParam String usrIdOrNameOrEmailOrPhone,
                                            @RequestParam String aCheckCode,
                                            Model model,
                                            HttpSession session){
        if (usrIdOrNameOrEmailOrPhone==null || "".equals(usrIdOrNameOrEmailOrPhone)){
            model.addAttribute("fpMsg",INTEGER_NULL);
            return "adminForgetPassword";
        }

        if(aCheckCode==null || "".equals(aCheckCode)){
            model.addAttribute("fpMsg",INTEGER_NULL);
            return "adminForgetPassword";
        }
        Admin existAdmin = adminService.findExistAdmin(usrIdOrNameOrEmailOrPhone);
        if (existAdmin==null){
            model.addAttribute("fpMsg",INTEGER_NULL);
            return "adminForgetPassword";
        }
        if (existAdmin.getAEmail()==null){
            model.addAttribute("fpMsg",INTEGER_NULL);
            return "adminForgetPassword";
        }

        if (!aCheckCode.equals(session.getAttribute("curAdminForgetPasswordCheckCode").toString())){
            model.addAttribute("fpMsg",INTEGER_NULL);
            return "adminForgetPassword";
        }
        System.out.println(session.getAttribute("curAdminForgetPasswordCheckCode").toString());

        util.sendMail(ADMIN_PASSWORD_SUBJECT,ADMIN_PASSWORD_TEXT+existAdmin.getAPassword(),DEFAULT_MAIL_SENDER,existAdmin.getAEmail());
        model.addAttribute("fpMsg",INTEGER_NULL+1);
        return "adminForgetPassword";
    }
}
