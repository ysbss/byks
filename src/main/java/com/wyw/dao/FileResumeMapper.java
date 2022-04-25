package com.wyw.dao;

import com.wyw.pojo.FileResume;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 * */
@Repository
public interface FileResumeMapper {

    /**
     * 增加学生简历
     * @param fileResume
     * @return int
     * */
    int addFileResume(FileResume fileResume);

    /**
     * 更新学生简历
     * @param fileResume
     * @return int
     * */
    int updateFileResume(Map<String,Object> fileResume);

    /**
     * 通过文件主键查询文件
     * @param fFileID
     * @return fileResume
     * */
    FileResume fetchFileResumeByfId(String fFileID);

    /**
     * 查询所有简历根据不同条件
     * @param fileResume
     * @return list
     * */
    List<FileResume> fetchFileResumesList(Map<String,Object> fileResume);


}
