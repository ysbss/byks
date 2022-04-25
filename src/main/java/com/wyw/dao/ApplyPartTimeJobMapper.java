package com.wyw.dao;

import com.wyw.pojo.ApplyPartTimeJob;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 鱼酥不是叔
 * */
@Repository
public interface ApplyPartTimeJobMapper {
    int addApplyPartTimeJob(ApplyPartTimeJob applyPartTimeJob);

    int comDealApplyPartTimeJob(Map<String ,Object> comDealMap);

    ApplyPartTimeJob isRepeatedInfoInApplyPartTimeJob(Map<String,Object> applyInfo);

    List<Map<String,Object>> fetchApplyPartTimeJobsBycIdAndSearchInfo(Map<String,Object> cIdAndSearchInfo);

    List<Map<String,Object>> fetchApplyPartTimeJobsBysIdAndSearchInfo(Map<String,Object> sIdAndSearchInfo);
}
