package com.wyw.service;

import com.wyw.pojo.PartTimeJob;

import java.util.List;
import java.util.Map;

/**
 * @author WYW
 */
public interface PartTimeJobService {

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
     * 查询所有兼职
     * @param partTimeJob
     * @return list
     * 用在删除公司的地方了
     * */
    List<PartTimeJob> fetchAllPartTimeJobs(PartTimeJob partTimeJob);

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
     *
     * @author WYW
     * 主要用于展示所有的兼职信息
     *
     * */
    List<Map<String,Object>> getAllPartTimeJobs(String partTimeJobSearchInfo);

    /**
     * 显示主页的8条精品信息
     * @return List<Map>
     * */
    List<Map<String,Object>> getAllPageSpecialPartTimeJobs();

    Map<String,Object> fetchSpcPartTimeJobByPid(Long pId);

    /**
     * 展示最新的两条精品兼职信息
     *
     * */
    List<Map<String,Object>> fetchLatestSpcPartTimeJob();

    /**
     *
     * 展示首页的四张图片
     * */
    List<Map<String,Object>> fetchImgParTimeJobs();

}
