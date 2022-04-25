package com.wyw.service;

import com.wyw.pojo.ApplyPartTimeJob;

import java.util.List;
import java.util.Map;

public interface ApplyPartTimeJobService {
    int addApplyPartTimeJob(ApplyPartTimeJob applyPartTimeJob);

    int comDealApplyPartTimeJob(Map<String ,Object> comDealMap);

    ApplyPartTimeJob isRepeatedInfoInApplyPartTimeJob(Map<String,Object> applyInfo);

    List<Map<String,Object>> fetchApplyPartTimeJobsBycIdAndSearchInfo(Map<String,Object> cIdAndSearchInfo);

    List<Map<String,Object>> fetchApplyPartTimeJobsBysIdAndSearchInfo(Map<String,Object> sIdAndSearchInfo);
}
