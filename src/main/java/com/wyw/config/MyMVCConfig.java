package com.wyw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author WYW
 */

@Configuration
public class MyMVCConfig implements WebMvcConfigurer {

    @Value("${company.img.path}")
    String comImgPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("dl");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/main.html").setViewName("index_1");

    }


    @Bean
    public HandlerInterceptor getMyLoginHandlerInterceptor(){
        return new  LoginHandlerInterceptor();
    }//bean的意思是将这个交给了spring容器管理

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginHandlerInterceptor())
        registry.addInterceptor(getMyLoginHandlerInterceptor())
                //网上说这样就可以在拦截器中注册mapper
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html","/","/toIndexPage",
                        "/toBzPage","/toBjPage","/toBftPage","/toZzyPage","/toLocalInformationPage/{lId}","/loginOut","/toBftMorePage","/toServiceProvidePage/{spId}","/toStuAdd","/toStuForgetPassword","/toComAdd","/toComForgetPassword","/toAdminAdd","/toAdminForgetPassword","/toWebServiceProtocolPage",
                        "/admin/adminLogin","/admin/addAdmin","/admin/adminFetchPasswordByEmail","/admin/getAdminRandomRegisterCheckCode","/admin/getAdminForgerPasswordRandomCheckCode",
                        "/company/companyLogin","/company/addCom","/company/comFetchPasswordByEmail","/company/comRevisePassword/{cId}",
                        "/student/studentLogin","/student/addStu","/student/stuFetchPasswordByEmail","/student/stuRevisePassword/{sId}",
                        "/ServiceProvide/**",
                        "/ApplyPartTimeJob/**",
                        "/LocalInformation/**",
                        "/PartTimeJob/fetchSpcPartTimeJob/{pId}","/PartTimeJob/fetchAllPartTimeJob/{pageNum}",
//                        "file:/"+comImgPath,
                        "/toBftPage","/toZzyPage/{pId}",
                        "/css/*","/js/**","/images/**");
//        这里不推荐用static/**会导致重定向后静态资源无法导入
    }


}
