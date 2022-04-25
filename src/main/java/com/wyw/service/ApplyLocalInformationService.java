package com.wyw.service;

import com.wyw.pojo.ApplyLocalInformation;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
public interface ApplyLocalInformationService {

    /**
     * @param applyInfo
     * @return ApplyLocalInformation
     * 判断是否重复
     * */
    ApplyLocalInformation isRepeatedInfoInApplyLocalInformation (Map<String,Object> applyInfo);


    /**
     * @param applyLocalInformation
     * @return int
     * 插入申请表
     * */
    int addApplyLocalInformation(ApplyLocalInformation applyLocalInformation);


    /**
     * @param cIdAndSearchInfo
     * 公司搜索申请表
     * */
    List<Map<String,Object>> fetchApplyLocalInformationBycIdAndSearchInfo(Map<String,Object> cIdAndSearchInfo);


    /**
     * @param sIdAndSearchInfo
     * 学生搜索申请表
     * */
    List<Map<String,Object>> fetchApplyLocalInformationBysIdAndSearchInfo(Map<String,Object> sIdAndSearchInfo);


    /**
     * @param comDealMap
     * @return int
     * 公司处理服务提供申请
     * */
    int comDealApplyLocalInformation(Map<String ,Object> comDealMap);

}
