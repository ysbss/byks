package com.wyw.service;

import com.wyw.dao.PartTimeJobMapper;
import com.wyw.pojo.PartTimeJob;
import com.wyw.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wyw.utils.FinalStaticValue;

import java.util.List;
import java.util.Map;

import static com.wyw.utils.FinalStaticValue.HOMEPAGE_IMG_CAPACITY;

/**
 * @author 鱼酥不是叔
 */
@Service("PartTimeJobServiceImpl")
public class PartTimeJobServiceImpl implements PartTimeJobService{

    @Autowired
    PartTimeJobMapper partTimeJobMapper;

    @Override
    public int addPartTimeJob(PartTimeJob partTimeJob) {
        return partTimeJobMapper.addPartTimeJob(partTimeJob);
    }

    @Override
    public int updatePartTimeJob(PartTimeJob partTimeJob) {
        return partTimeJobMapper.updatePartTimeJob(partTimeJob);
    }

    @Override
    public int deletePartTimeJobByPid(Long pId) {
        return partTimeJobMapper.deletePartTimeJobByPid(pId);
    }

    @Override
    public List<Map<String, Object>> fetchApproximatePartTimeJobByPid(Long pId) {
        return partTimeJobMapper.fetchApproximatePartTimeJobByPid(pId);
    }

    @Override
    public List<Map<String, Object>> getAllPartTimeJobsByCidAndSearchInfo(Map<String,Object> cIdAndSearchInfo) {
        return partTimeJobMapper.getAllPartTimeJobsByCidAndSearchInfo(cIdAndSearchInfo);
    }

    @Override
    public List<Map<String,Object>> getAllPartTimeJobs(String partTimeJobSearchInfo) {
        return partTimeJobMapper.getAllPartTimeJobs(partTimeJobSearchInfo);
    }

    @Override
    public List<Map<String,Object>> getAllPageSpecialPartTimeJobs() {
        return partTimeJobMapper.getAllPageSpecialPartTimeJobs();
    }

    @Override
    public Map<String, Object> fetchSpcPartTimeJobByPid(Long pId) {
        return partTimeJobMapper.fetchSpcPartTimeJobByPid(pId);
    }

    @Override
    public List<Map<String, Object>> fetchLatestSpcPartTimeJob() {
        return partTimeJobMapper.fetchLatestSpcPartTimeJob();
    }

    @Override
    public List<Map<String, Object>> fetchImgParTimeJobs() {
        List<Map<String, Object>> ImgMaps = partTimeJobMapper.fetchImgParTimeJobs();
        if (ImgMaps.size()<HOMEPAGE_IMG_CAPACITY){
            return new Util().addToFourElement(ImgMaps);
        }else {
            return partTimeJobMapper.fetchImgParTimeJobs();
        }

    }
}
