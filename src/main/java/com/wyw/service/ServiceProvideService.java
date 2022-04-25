package com.wyw.service;

import com.wyw.pojo.ServiceProvide;

import java.util.List;
import java.util.Map;

public interface ServiceProvideService {


    /**
     * 增加服务
     * @param serviceProvide
     * @return int
     * */
    int addServiceProvide(ServiceProvide serviceProvide);

    /**
     * 修改服务
     * @param serviceProvide
     * @return int
     * */
    int updateServiceProvide(ServiceProvide serviceProvide);


    /**
     *将有多少种服务类型查询出来
     * @return list
     * */
    List<Integer> fetchServiceProvideKinds();

    /**
     * 将每个服务的具体服务查询出来
     * @return list
     * */
    List<Map<String,Object>> fetchServiceProvideSpecificKinds();

    /**
     *将精品服务查询出来
     * @return list
     * */
    List<Map<String,Object>> fetchHomePageServiceProvides();

    /**
     *将所有服务查询出来
     * @param searchInfo
     * @return list
     * */
    List<Map<String,Object>> fetchAllServiceProvides(Map<String,Object> searchInfo);


    /**
     * 查询单个serviceProvide
     * @param spId
     * @return Map
     * */
    Map<String,Object> fetchServiceProvideBySpId(Long spId);

    /**
     * 更新浏览人数
     * @param spId
     * @return int
     * */
    int updateServiceProvideBrowsePersonBySpId(Long spId);

    /**
     * 更新预约人数
     * @param spId
     * @return int
     * */
    int updateServiceProvideAppointmentBySpId(Long spId);


    /**
     * 查询近似信息
     * @param spKindAndSpId
     * @return list
     * */
    List<Map<String,Object>> fetchApproximateServiceProvidesBySpKindAndSpId(Map<String,Object> spKindAndSpId);

}
