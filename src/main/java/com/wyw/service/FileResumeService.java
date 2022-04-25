package com.wyw.service;


import com.wyw.pojo.FileResume;

import java.util.List;
import java.util.Map;

public interface FileResumeService {
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
