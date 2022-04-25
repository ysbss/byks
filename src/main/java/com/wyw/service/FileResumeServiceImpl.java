package com.wyw.service;

import com.wyw.dao.FileResumeMapper;
import com.wyw.pojo.FileResume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Service("FileResumeServiceImpl")
public class FileResumeServiceImpl implements FileResumeService{

    @Autowired
    FileResumeMapper fileResumeMapper;

    @Override
    public int addFileResume(FileResume fileResume) {
        return fileResumeMapper.addFileResume(fileResume);
    }

    @Override
    public int updateFileResume(Map<String, Object> fileResume) {
        return fileResumeMapper.updateFileResume(fileResume);
    }

    @Override
    public FileResume fetchFileResumeByfId(String fFileID) {
        return fileResumeMapper.fetchFileResumeByfId(fFileID);
    }

    @Override
    public List<FileResume> fetchFileResumesList(Map<String, Object> fileResume) {
        return fileResumeMapper.fetchFileResumesList(fileResume);
    }
}
