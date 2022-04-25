package com.wyw.service;


import com.wyw.pojo.FileCompany;
import org.springframework.stereotype.Repository;

@Repository
public interface FileCompanyService {

    /**
     * 添加公司文件
     * */
    int addFileCompany(FileCompany fileCompany);

    /**
     * @param fileCompany
     * 更新公司文件
     *
     * */

    int updateFileCompany(FileCompany fileCompany);

    /**
     *
     * 查看公司是否重复上传头像
     * */
    FileCompany isRepeatedCompanyFile(Long fFileCid);
}
