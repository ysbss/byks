package com.wyw.service;

import com.wyw.dao.WebAdviceMapper;
import com.wyw.pojo.WebAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Service("WebAdviceServiceImpl")
public class WebAdviceServiceImpl implements WebAdviceService {

    @Autowired
    WebAdviceMapper webAdviceMapper;

    @Override
    public int addWebAdvice(Map<String, Object> webAdvice) {
        return webAdviceMapper.addWebAdvice(webAdvice);
    }

    @Override
    public int updateWebAdvice(Map<String, Object> webAdvice) {
        return webAdviceMapper.updateWebAdvice(webAdvice);
    }

    @Override
    public int deleteWebAdvice(Map<String, Object> webAdvice) {
        return webAdviceMapper.deleteWebAdvice(webAdvice);
    }

    @Override
    public List<WebAdvice> fetchWebAdvice(Map<String, Object> webAdvice) {
        return webAdviceMapper.fetchWebAdvice(webAdvice);
    }
}
