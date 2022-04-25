package com.wyw.service;

import com.wyw.dao.FileCompanyMapper;
import com.wyw.pojo.FileCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 鱼酥不是叔
 * */
@Service("FileCompanyServiceImpl")
public class FileCompanyServiceImpl implements FileCompanyService{

    @Autowired
    FileCompanyMapper fileCompanyMapper;

    @Override
    public int addFileCompany(FileCompany fileCompany) {
        return fileCompanyMapper.addFileCompany(fileCompany);
    }

    @Override
    public int updateFileCompany(FileCompany fileCompany) {
        return fileCompanyMapper.updateFileCompany(fileCompany);
    }

    @Override
    public FileCompany isRepeatedCompanyFile(Long fFileCid) {
        return fileCompanyMapper.isRepeatedCompanyFile(fFileCid);
    }
}