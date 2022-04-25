package com.wyw.service;


import com.wyw.dao.LocalInformationMapper;
import com.wyw.pojo.LocalInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author 鱼酥不是叔
 */
@Service("LocalInformationServiceImpl")
public class LocalInformationServiceImpl implements LocalInformationService {
    @Autowired
    LocalInformationMapper localInformationMapper;

    @Override
    public int addLocalInformation(LocalInformation localInformation) {
        return localInformationMapper.addLocalInformation(localInformation);
    }

    @Override
    public int updateLocalInformation(LocalInformation localInformation) {
        return localInformationMapper.updateLocalInformation(localInformation);
    }

    @Override
    public Integer fetchLocalInformationKindBySpecificKind(String specificKind) {
        return localInformationMapper.fetchLocalInformationKindBySpecificKind(specificKind);
    }

    @Override
    public List<Integer> fetchLocalInformationKinds() {
        return localInformationMapper.fetchLocalInformationKinds();
    }

    @Override
    public List<Map<String, Object>> fetchLocalInformationSpecificKinds() {
        return localInformationMapper.fetchLocalInformationSpecificKinds();
    }

    @Override
    public List<Map<String, Object>> fetchHomePageLocalInformation() {
        return localInformationMapper.fetchHomePageLocalInformation();
    }

    @Override
    public List<Map<String, Object>> fetchAllLocalInformation(Map<String, Object> searchInfo) {
        return localInformationMapper.fetchAllLocalInformation(searchInfo);
    }

    @Override
    public Map<String,Object> fetchLocalInformationBylId(Long lId) {
        return localInformationMapper.fetchLocalInformationBylId(lId);
    }

    @Override
    public int updateLocalInformationBrowsePersonByLid(Long lId) {
        return localInformationMapper.updateLocalInformationBrowsePersonByLid(lId);
    }

    @Override
    public int updateLocalInformationAppointmentByLid(Long lId) {
        return localInformationMapper.updateLocalInformationAppointmentByLid(lId);
    }

    @Override
    public List<Map<String, Object>> fetchApproximateLocalInformationBylKindAndLid(Map<String,Object> lKindAndLid) {
        return localInformationMapper.fetchApproximateLocalInformationBylKindAndLid(lKindAndLid);
    }


}
