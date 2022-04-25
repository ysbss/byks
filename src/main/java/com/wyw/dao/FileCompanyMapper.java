package com.wyw.dao;

import com.wyw.pojo.FileCompany;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 鱼酥不是叔
 * */
@Repository
public interface FileCompanyMapper {

    /**
     * @param fileCompany
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
     * @param fFileCid
     * 查看公司是否重复上传头像
     * */
    FileCompany isRepeatedCompanyFile(Long fFileCid);
}
