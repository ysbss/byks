package com.wyw.dao;


import com.wyw.pojo.LocalInformation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Repository
public interface LocalInformationMapper {



    /**
     * 增加本地服务
     * @param localInformation
     * @return int
     * */
    int addLocalInformation(LocalInformation localInformation);

    /**
     * 更新本地服务
     * @param localInformation
     * @return int
     * */
    int updateLocalInformation(LocalInformation localInformation);


    /**
     * 通过具体种类查出所属种类
     * @param specificKind
     * @return int
     * */
    Integer fetchLocalInformationKindBySpecificKind(String specificKind);

    /**
     *将有多少种服务类型查询出来
     * @return list
     * */
    List<Integer> fetchLocalInformationKinds();

    /**
     * 将每个服务的具体服务查询出来
     * @return list
     * */
    List<Map<String,Object>> fetchLocalInformationSpecificKinds();

    /**
     * 根据选择的本地信息显示具体本地信息
     * @return string
     * */
    List<String> fetchLocalInformationSpecificKindsByLocalInformationKind(Integer lKind);

    /**
     *将有多少精品查询出来
     * @return list
     * */
    List<Map<String,Object>> fetchHomePageLocalInformation();

    /**
     *将所有信息查询出来
     * @param searchInfo
     * @return list
     * */
    List<Map<String,Object>> fetchAllLocalInformation(Map<String,Object> searchInfo);


    /**
     * 查询单个localInformation
     * @param lId
     * @return Map
     * */
    Map<String,Object> fetchLocalInformationBylId(Long lId);

    /**
     * 更新浏览人数
     * @param lId
     * @return int
     * */
    int updateLocalInformationBrowsePersonByLid(Long lId);

    /**
     * 更新预约人数
     * @param lId
     * @return int
     * */
    int updateLocalInformationAppointmentByLid(Long lId);

    /**
     * 查询近似信息
     * @param lKindAndLid
     * @return list
     * */
    List<Map<String,Object>> fetchApproximateLocalInformationBylKindAndLid(Map<String,Object> lKindAndLid);

}
