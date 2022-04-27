package com.wyw.service;

import com.wyw.dao.AdminMapper;
import com.wyw.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Service("AdminServiceImpl")
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin fetchAdminById(Long aId) {
        return adminMapper.fetchAdminById(aId);
    }

    @Override
    public int addAdmin(Map<String, Object> admin) {
        return adminMapper.addAdmin(admin);
    }

    @Override
    public int updateAdmin(Map<String, Object> admin) {
        return adminMapper.updateAdmin(admin);
    }

    @Override
    public List<Admin> fetchAdminsList(Map<String, Object> admin) {
        return adminMapper.fetchAdminsList(admin);
    }

    @Override
    public Admin findExistAdmin(String findExistAdmin) {
        return adminMapper.findExistAdmin(findExistAdmin);
    }
}
