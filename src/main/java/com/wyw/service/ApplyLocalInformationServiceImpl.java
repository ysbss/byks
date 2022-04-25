package com.wyw.service;

import com.wyw.dao.ApplyLocalInformationMapper;
import com.wyw.pojo.ApplyLocalInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author 鱼酥不是叔
 */
@Service("ApplyLocalInformationServiceImpl")
public class ApplyLocalInformationServiceImpl implements ApplyLocalInformationService{


    @Autowired
    ApplyLocalInformationMapper applyLocalInformationMapper;

    @Override
    public ApplyLocalInformation isRepeatedInfoInApplyLocalInformation(Map<String, Object> applyInfo) {
        return applyLocalInformationMapper.isRepeatedInfoInApplyLocalInformation(applyInfo);
    }

    @Override
    public int addApplyLocalInformation(ApplyLocalInformation applyLocalInformation) {
        return applyLocalInformationMapper.addApplyLocalInformation(applyLocalInformation);
    }

    @Override
    public List<Map<String, Object>> fetchApplyLocalInformationBycIdAndSearchInfo(Map<String, Object> cIdAndSearchInfo) {
        return applyLocalInformationMapper.fetchApplyLocalInformationBycIdAndSearchInfo(cIdAndSearchInfo);
    }

    @Override
    public List<Map<String, Object>> fetchApplyLocalInformationBysIdAndSearchInfo(Map<String, Object> sIdAndSearchInfo) {
        return applyLocalInformationMapper.fetchApplyLocalInformationBysIdAndSearchInfo(sIdAndSearchInfo);
    }

    @Override
    public int comDealApplyLocalInformation(Map<String, Object> comDealMap) {
        return applyLocalInformationMapper.comDealApplyLocalInformation(comDealMap);
    }
}
