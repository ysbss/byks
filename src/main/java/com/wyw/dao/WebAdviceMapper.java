package com.wyw.dao;

import com.wyw.pojo.WebAdvice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Repository
public interface WebAdviceMapper {
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
