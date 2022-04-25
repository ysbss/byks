package com.wyw.service;

import com.wyw.dao.CompanyMapper;
import com.wyw.pojo.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Service("CompanyServiceImpl")
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public Company fetchCompanyByName(String cName) {
        return companyMapper.fetchCompanyByName(cName);
    }

    @Override
    public Company fetchCompanyByCid(Long cId) {
        return companyMapper.fetchCompanyByCid(cId);
    }

    @Override
    public List<Company> fetchCompaniesList(Map<String, Object> company) {
        return companyMapper.fetchCompaniesList(company);
    }

    @Override
    public int addCompany(Map<String, Object> company) {
        return companyMapper.addCompany(company);
    }

    @Override
    public int updateCompany(Map<String, Object> company) {
        return companyMapper.updateCompany(company);
    }
}
