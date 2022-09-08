package com.wyw.service;

import com.alibaba.fastjson.JSON;
import com.wyw.dao.CompanyMapper;
import com.wyw.pojo.Admin;
import com.wyw.pojo.Company;
import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Service("CompanyServiceImpl")
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyMapper companyMapper;

    @Resource
    RedisUtils redisUtils;

    @Override
    public Company fetchCompanyByName(String cName) {
        if (redisUtils.hget(FinalStaticValue.TABLE_COMPANY, cName) == null){
            redisUtils.hset(FinalStaticValue.TABLE_COMPANY, cName, companyMapper.fetchCompanyByName(cName),3600);
        }
                return JSON.parseObject(JSON.toJSONString(redisUtils.hget(FinalStaticValue.TABLE_COMPANY, cName)), Company.class);
    }

    @Override
    public Company fetchCompanyByCid(Long cId) {
        return companyMapper.fetchCompanyByCid(cId);
    }

    @Override
    public Company fetchExistCompany(String fetchExistCompany) {
        return companyMapper.fetchExistCompany(fetchExistCompany);
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
