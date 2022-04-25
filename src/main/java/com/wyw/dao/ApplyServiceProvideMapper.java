package com.wyw.dao;

import com.wyw.pojo.ApplyServiceProvide;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Repository
public interface ApplyServiceProvideMapper {

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
     * 公司搜索申请表
     * */
    List<Map<String,Object>> fetchApplyServiceProvidesBycIdAndSearchInfo(Map<String,Object> cIdAndSearchInfo);

    /**
     * @param sIdAndSearchInfo
     * 学生搜索申请表
     * */
    List<Map<String,Object>> fetchApplyServiceProvidesBysIdAndSearchInfo(Map<String,Object> sIdAndSearchInfo);

    /**
     * @param comDealMap
     * 公司处理服务提供申请
     * */
    int comDealApplyServiceProvide(Map<String ,Object> comDealMap);

}
