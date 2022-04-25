package com.wyw.service;

import com.wyw.dao.ApplyServiceProvideMapper;
import com.wyw.pojo.ApplyServiceProvide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author 鱼酥不是叔
 */
@Service("ApplyServiceProvideServiceImpl")
public class ApplyServiceProvideServiceImpl implements ApplyServiceProvideService{

    @Autowired
    ApplyServiceProvideMapper applyServiceProvideMapper;

    @Override
    public ApplyServiceProvide isRepeatedInfoInApplyServiceProvide(Map<String, Object> applyInfo) {
        return applyServiceProvideMapper.isRepeatedInfoInApplyServiceProvide(applyInfo);
    }

    @Override
    public int addApplyServiceProvide(ApplyServiceProvide applyServiceProvide) {
        return applyServiceProvideMapper.addApplyServiceProvide(applyServiceProvide);
    }

    @Override
    public List<Map<String, Object>> fetchApplyServiceProvidesBycIdAndSearchInfo(Map<String, Object> cIdAndSearchInfo) {
        return applyServiceProvideMapper.fetchApplyServiceProvidesBycIdAndSearchInfo(cIdAndSearchInfo);
    }

    @Override
    public List<Map<String, Object>> fetchApplyServiceProvidesBysIdAndSearchInfo(Map<String, Object> sIdAndSearchInfo) {
        return applyServiceProvideMapper.fetchApplyServiceProvidesBysIdAndSearchInfo(sIdAndSearchInfo);
    }

    @Override
    public int comDealApplyServiceProvide(Map<String, Object> comDealMap) {
        return applyServiceProvideMapper.comDealApplyServiceProvide(comDealMap);
    }
}
