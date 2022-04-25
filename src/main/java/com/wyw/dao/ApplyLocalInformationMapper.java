package com.wyw.dao;

import com.wyw.pojo.ApplyLocalInformation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Repository
public interface ApplyLocalInformationMapper {



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
     * 公司处理服务提供申请
     * */
    int comDealApplyLocalInformation(Map<String ,Object> comDealMap);



}
