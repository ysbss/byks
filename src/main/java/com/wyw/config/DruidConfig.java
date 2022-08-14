package com.wyw.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Configuration
public class DruidConfig {

//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
//    public DataSource druidDataSource(){
//        return new DruidDataSource();
//    }
//用了druid-boot-starter的话就不用自己手动配置了

    /**
     * 后台监控
     * @return ServletRegistrationBean
     * */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet(){
        ServletRegistrationBean<StatViewServlet> statViewServletServletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        Map<String,String> hm=new HashMap<String,String>();
        hm.put("loginUsername","admin");
        hm.put("loginPassword","1234");

//        hm.put("allow","localhost");
        hm.put("allow","");
        //配置本地不需要localhost会报错，直接空字符串即可
        hm.put("deny","127.0.0.1");

        statViewServletServletRegistrationBean.setInitParameters(hm);

        return statViewServletServletRegistrationBean;

    }

    @Bean
    public FilterRegistrationBean<Filter> webStatFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<Filter>();
        filterFilterRegistrationBean.setFilter(new WebStatFilter());
        Map<String,String> hm=new HashMap<String,String>();

        hm.put("exclusions","*.js,*.css,/druid/*");

        filterFilterRegistrationBean.setInitParameters(hm);
        return filterFilterRegistrationBean;
    }
}
