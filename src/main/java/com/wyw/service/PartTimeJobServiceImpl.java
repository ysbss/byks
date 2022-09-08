package com.wyw.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wyw.dao.PartTimeJobMapper;
import com.wyw.pojo.PartTimeJob;
import com.wyw.utils.RedisUtils;
import com.wyw.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wyw.utils.FinalStaticValue;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.wyw.utils.FinalStaticValue.HOMEPAGE_IMG_CAPACITY;
import static com.wyw.utils.FinalStaticValue.TABLE_PARTTIMEJOB;

/**
 * @author 鱼酥不是叔
 */
@Service("PartTimeJobServiceImpl")
public class PartTimeJobServiceImpl implements PartTimeJobService{

    @Autowired
    PartTimeJobMapper partTimeJobMapper;

    @Resource
    RedisUtils redisUtils;

    @Override
    public int addPartTimeJob(PartTimeJob partTimeJob) {

        redisUtils.scan("*"+TABLE_PARTTIMEJOB+"*").forEach(ptj->{
            redisUtils.del(ptj);
        });
        return partTimeJobMapper.addPartTimeJob(partTimeJob);
    }

    @Override
    public int updatePartTimeJob(PartTimeJob partTimeJob) {
        System.out.println("scan:"+redisUtils.scan("*"+TABLE_PARTTIMEJOB+"*"));
        redisUtils.scan("*"+TABLE_PARTTIMEJOB+"*").forEach(ptj->{
            System.out.println("ptj:"+ptj);
            System.out.println(ptj.getClass());
            redisUtils.del(ptj);
        });
        return partTimeJobMapper.updatePartTimeJob(partTimeJob);
    }

    @Override
    public int deletePartTimeJobByPid(Long pId) {
        redisUtils.scan("*"+TABLE_PARTTIMEJOB+"*").forEach(ptj->{
            redisUtils.del(ptj);
        });
        return partTimeJobMapper.deletePartTimeJobByPid(pId);
    }

    @Override
    public List<PartTimeJob> fetchAllPartTimeJobs(PartTimeJob partTimeJob) {

        return partTimeJobMapper.fetchAllPartTimeJobs(partTimeJob);

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
//        if (redisUtils.hget(TABLE_PARTTIMEJOB,"AllPartTimeJobs"+partTimeJobSearchInfo)==null
//                ||redisUtils.hget(TABLE_PARTTIMEJOB,"AllPartTimeJobs"+partTimeJobSearchInfo).toString().isEmpty()){
//            redisUtils.hset(TABLE_PARTTIMEJOB,"AllPartTimeJobs"+partTimeJobSearchInfo,partTimeJobMapper.getAllPartTimeJobs(partTimeJobSearchInfo),3600);
//        }
//        return JSON.parseObject(JSON.toJSONString(partTimeJobMapper.getAllPartTimeJobs(partTimeJobSearchInfo)),new TypeReference<List<Map<String,Object>>>(){});
        return partTimeJobMapper.getAllPartTimeJobs(partTimeJobSearchInfo);
    }

    @Override
    public List<Map<String,Object>> getAllPageSpecialPartTimeJobs() {
        return partTimeJobMapper.getAllPageSpecialPartTimeJobs();
    }

    @Override
    public Map<String, Object> fetchSpcPartTimeJobByPid(Long pId) {
        if (redisUtils.hget(TABLE_PARTTIMEJOB, String.valueOf(pId))==null
                ||redisUtils.hget(TABLE_PARTTIMEJOB, String.valueOf(pId)).toString().isEmpty()){
            redisUtils.hset(TABLE_PARTTIMEJOB, String.valueOf(pId), partTimeJobMapper.fetchSpcPartTimeJobByPid(pId),3600);
        }
//        return partTimeJobMapper.fetchSpcPartTimeJobByPid(pId);
//        return redisUtils.hmget(FinalStaticValue.TABLE_PARTTIMEJOB+ String.valueOf(pId)).entrySet().stream().collect(Collectors.toMap(e -> String.valueOf(e.getKey()), Map.Entry::getValue));
        return JSON.parseObject(JSON.toJSONString(redisUtils.hget(FinalStaticValue.TABLE_PARTTIMEJOB, String.valueOf(pId))),new TypeReference<Map<String, Object>>(){}.getType());
    }

    @Override
    public List<Map<String, Object>> fetchLatestSpcPartTimeJob() {
//        if (JSON.parseObject(JSON.toJSONString(redisUtils.hget(FinalStaticValue.TABLE_PARTTIMEJOB,"LatestSpcPartTimeJob")),new TypeReference<List<Map<String, Object>>>(){}.getType())
//                ==null||JSON.parseObject(JSON.toJSONString(redisUtils.hget(FinalStaticValue.TABLE_PARTTIMEJOB,"LatestSpcPartTimeJob")),new TypeReference<List<Map<String, Object>>>(){}.getType()).toString().isEmpty()){
        if (redisUtils.hget(FinalStaticValue.TABLE_PARTTIMEJOB,"LatestSpcPartTimeJob")
                ==null||redisUtils.hget(FinalStaticValue.TABLE_PARTTIMEJOB,"LatestSpcPartTimeJob").toString().isEmpty()){

//            for (Map<String,Object> m:
//            partTimeJobMapper.fetchLatestSpcPartTimeJob()) {
//                redisUtils.lSet("LatestSpcPartTimeJob",m);
//            }
            redisUtils.hset(FinalStaticValue.TABLE_PARTTIMEJOB,"LatestSpcPartTimeJob",partTimeJobMapper.fetchLatestSpcPartTimeJob(),3600);
//            System.out.println(partTimeJobMapper.fetchLatestSpcPartTimeJob());
        }
//        return partTimeJobMapper.fetchLatestSpcPartTimeJob();
//        System.out.println(redisUtils.lGet("LatestSpcPartTimeJob",0,-1));
//        System.out.println(util.listTransToMap(redisUtils.lGet("LatestSpcPartTimeJob",0,-1)));
//        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < redisUtils.lGet("LatestSpcPartTimeJob", 0, -1).size(); i++) {
//           maps.add(JSON.parseObject(JSON.toJSONString(redisUtils.lGetIndex("LatestSpcPartTimeJob",i)),Map.class));
//        }
//        System.out.println(JSON.parseArray(
//                JSON.toJSONString(
//                        redisUtils.lGet("LatestSpcPartTimeJob", 0, -1).toString()),Map.class));
//        System.out.println(maps);
//        return JSON.parseObject(
//                JSON.toJSONString(
//                        redisUtils.lGet("LatestSpcPartTimeJob", 0, -1)),new TypeReference<List<Map<String, Object>>>(){}.getType());
//        Object latestSpcPartTimeJob = JSON.parseObject(JSON.toJSONString(redisUtils.hget(TABLE_PARTTIMEJOB, "LatestSpcPartTimeJob")), new TypeReference<List<Map<String, Object>>>() {
//        }.getType());
//        System.out.println(latestSpcPartTimeJob.getClass());
//        System.out.println(latestSpcPartTimeJob);
        return JSON.parseObject(JSON.toJSONString(redisUtils.hget(FinalStaticValue.TABLE_PARTTIMEJOB,"LatestSpcPartTimeJob")),new TypeReference<List<Map<String, Object>>>(){}.getType());
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
