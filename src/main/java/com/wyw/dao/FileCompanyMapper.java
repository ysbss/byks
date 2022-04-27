package com.wyw.dao;

import com.wyw.pojo.FileCompany;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 鱼酥不是叔
 * */
@Repository
public interface FileCompanyMapper {

    /**
     * 添加公司文件
     * @param fileCompany
     * @return int
     * */
    int addFileCompany(FileCompany fileCompany);

    /**
     * 更新公司文件
     * @param fileCompany
     * @return int
     * */

    int updateFileCompany(FileCompany fileCompany);

    /**
     * 查看公司是否重复上传头像
     * @param fFileCid
     * @return fc
     * */
    FileCompany isRepeatedCompanyFile(Long fFileCid);

    /**
     * 查询公司文件列表（暂时是一个公司上传一个文件）
     * @param fileCompany
     * @return list
     * */
    List<FileCompany> fetchFileCompanyList(Map<String,Object> fileCompany);
}
