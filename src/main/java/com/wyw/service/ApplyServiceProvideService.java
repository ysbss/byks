package com.wyw.service;

import com.wyw.pojo.ApplyServiceProvide;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
public interface ApplyServiceProvideService {
    /**
     * @param  applyInfo
     * @return ApplyServiceProvide
     * 判断是否重复
     * */
    ApplyServiceProvide isRepeatedInfoInApplyServiceProvide(Map<String,Object> applyInfo);

    /**
     * @param applyServiceProvide
     * @return  int
     * 插入申请表
     * */
    int addApplyServiceProvide(ApplyServiceProvide applyServiceProvide);


    /**
     * @param cIdAndSearchInfo
     * @return list
     * 公司搜索申请表
     * */
    List<Map<String,Object>> fetchApplyServiceProvidesBycIdAndSearchInfo(Map<String,Object> cIdAndSearchInfo);

    /**
     * @param sIdAndSearchInfo
     * @return list
     * 学生搜索申请表
     * */
    List<Map<String,Object>> fetchApplyServiceProvidesBysIdAndSearchInfo(Map<String,Object> sIdAndSearchInfo);

    /**
     * 公司处理服务提供申请
     * */
    int comDealApplyServiceProvide(Map<String ,Object> comDealMap);

}
