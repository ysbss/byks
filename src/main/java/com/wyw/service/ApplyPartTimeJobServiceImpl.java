package com.wyw.service;

import com.wyw.dao.ApplyPartTimeJobMapper;
import com.wyw.pojo.ApplyPartTimeJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Service("ApplyPartTimeJobServiceImpl")
public class ApplyPartTimeJobServiceImpl implements ApplyPartTimeJobService{

    @Autowired
    ApplyPartTimeJobMapper applyPartTimeJobMapper;

    @Override
    public int addApplyPartTimeJob(ApplyPartTimeJob applyPartTimeJob) {
        return applyPartTimeJobMapper.addApplyPartTimeJob(applyPartTimeJob);
    }

    @Override
    public int comDealApplyPartTimeJob(Map<String ,Object> comDealMap) {
        return applyPartTimeJobMapper.comDealApplyPartTimeJob(comDealMap);
    }

    @Override
    public ApplyPartTimeJob isRepeatedInfoInApplyPartTimeJob(Map<String, Object> applyInfo) {
        return applyPartTimeJobMapper.isRepeatedInfoInApplyPartTimeJob(applyInfo);
    }

    @Override
    public List<Map<String, Object>> fetchApplyPartTimeJobsBycIdAndSearchInfo(Map<String,Object> sIdAndSearchInfo) {
        return applyPartTimeJobMapper.fetchApplyPartTimeJobsBycIdAndSearchInfo(sIdAndSearchInfo);
    }

    @Override
    public List<Map<String, Object>> fetchApplyPartTimeJobsBysIdAndSearchInfo(Map<String,Object> sIdAndSearchInfo) {
        return applyPartTimeJobMapper.fetchApplyPartTimeJobsBysIdAndSearchInfo(sIdAndSearchInfo);
    }
}
