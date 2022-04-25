package com.wyw.dao;

import com.wyw.pojo.PartTimeJob;
import org.springframework.stereotype.Repository;

import java.net.InterfaceAddress;
import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Repository
public interface PartTimeJobMapper {

    /**
     * 新增兼职
     * @param partTimeJob
     * @return int
     * */
    int addPartTimeJob(PartTimeJob partTimeJob);

    /**
     * 修改兼职
     * @param partTimeJob
     * @return int
     * */
    int updatePartTimeJob(PartTimeJob partTimeJob);

    /**
     * 删除兼职信息
     * @param pId
     * @return int
     * */
    int deletePartTimeJobByPid(Long pId);

    /**
     * 获得近似兼职
     * @param pId
     * @return list
     * */
    List<Map<String,Object>> fetchApproximatePartTimeJobByPid(Long pId);

    /**
     * 公司查看自己发布的兼职
     * @param cIdAndSearchInfo
     * @return list
     * */
    List<Map<String,Object>> getAllPartTimeJobsByCidAndSearchInfo(Map<String,Object> cIdAndSearchInfo);


    /**
     * 主要用于展示所有的兼职信息
     * @param partTimeJobSearchInfo
     * @return list
     * */
    List<Map<String,Object>> getAllPartTimeJobs(String partTimeJobSearchInfo);

    /**
     * 主要用于展示所有的精品兼职信息
     * @return list
     * */
    List<Map<String,Object>> getAllPageSpecialPartTimeJobs();

    /**
     * 用于展示具体的兼职信息
     * @param pId
     * @return map
     * */
    Map<String,Object> fetchSpcPartTimeJobByPid(Long pId);

    /**
     * 展示最新的两条精品兼职信息
     * @return list
     * */
    List<Map<String,Object>> fetchLatestSpcPartTimeJob();


    /**
     * 展示首页的四张图片
     * @return list
     * */
    List<Map<String,Object>> fetchImgParTimeJobs();
}
