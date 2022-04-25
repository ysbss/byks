package com.wyw.dao;

import com.wyw.pojo.Company;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Repository
public interface CompanyMapper {
    /**
     * 主要用于公司登陆时，在数据库进行查询是否有这个用户有则进入页面，没有则返回错误
     * @param cName  学生id主键
     * @author WYW
     * @return company
     * */
    Company fetchCompanyByName(String cName);
    /**
     * 通过id查询所有公司信息
     * @param cId  学生id主键
     * @author WYW
     * @return company
     * */
    Company fetchCompanyByCid(Long cId);

    /**
     * 获得所有公司
     * @param company
     * @return list
     * */
    List<Company> fetchCompaniesList(Map<String,Object> company);
    /**
     * 增加公司
     * @param company
     * @return int
     * */
    int addCompany(Map<String,Object> company);
    /**
     * 修改公司
     * @param company
     * @return int
     * */
    int updateCompany(Map<String,Object> company);
}
