package com.wyw.service;

import com.wyw.dao.ServiceProvideMapper;
import com.wyw.pojo.ServiceProvide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Service("ServiceProvideServiceImpl")
public class ServiceProvideServiceImpl implements ServiceProvideService{

    @Autowired
    ServiceProvideMapper serviceProvideMapper;

    @Override
    public int addServiceProvide(ServiceProvide serviceProvide) {
        return serviceProvideMapper.addServiceProvide(serviceProvide);
    }

    @Override
    public int updateServiceProvide(ServiceProvide serviceProvide) {
        return serviceProvideMapper.updateServiceProvide(serviceProvide);
    }

    @Override
    public List<Integer> fetchServiceProvideKinds() {
        return serviceProvideMapper.fetchServiceProvideKinds();
    }

    @Override
    public List<Map<String, Object>> fetchServiceProvideSpecificKinds() {
        return serviceProvideMapper.fetchServiceProvideSpecificKinds();
    }

    @Override
    public List<String> fetchServiceProvideSpecificKindsByServiceProvideKind(Integer spKind) {
        return serviceProvideMapper.fetchServiceProvideSpecificKindsByServiceProvideKind(spKind);
    }

    @Override
    public List<Map<String, Object>> fetchHomePageServiceProvides() {
        return serviceProvideMapper.fetchHomePageServiceProvides();
    }

    @Override
    public List<Map<String, Object>> fetchAllServiceProvides(Map<String, Object> searchInfo) {
        return serviceProvideMapper.fetchAllServiceProvides(searchInfo);
    }

    @Override
    public Map<String, Object> fetchServiceProvideBySpId(Long spId) {
        return serviceProvideMapper.fetchServiceProvideBySpId(spId);
    }

    @Override
    public int updateServiceProvideBrowsePersonBySpId(Long spId) {
        return serviceProvideMapper.updateServiceProvideBrowsePersonBySpId(spId);
    }

    @Override
    public int updateServiceProvideAppointmentBySpId(Long spId) {
        return serviceProvideMapper.updateServiceProvideAppointmentBySpId(spId);
    }

    @Override
    public List<Map<String, Object>> fetchApproximateServiceProvidesBySpKindAndSpId(Map<String,Object> spKindAndSpId ) {
        return serviceProvideMapper.fetchApproximateServiceProvidesBySpKindAndSpId(spKindAndSpId);
    }
}
