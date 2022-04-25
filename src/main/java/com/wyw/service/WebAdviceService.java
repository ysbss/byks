package com.wyw.service;

import com.wyw.pojo.WebAdvice;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
public interface WebAdviceService {
    /**
     * 增加网站建议
     * @param webAdvice
     * @return int
     * */
    int addWebAdvice(Map<String,Object> webAdvice);
    /**
     * 修改网站建议
     * @param webAdvice
     * @return int
     * */
    int updateWebAdvice(Map<String,Object> webAdvice);
    /**
     * 删除网站建议
     * @param webAdvice
     * @return int
     * */
    int deleteWebAdvice(Map<String,Object> webAdvice);
    /**
     * 查询网站建议
     * @param webAdvice
     * @return WebAdvice
     * */
    List<WebAdvice> fetchWebAdvice(Map<String,Object> webAdvice);
}
