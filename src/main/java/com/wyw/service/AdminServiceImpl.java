package com.wyw.service;

import com.wyw.dao.AdminMapper;
import com.wyw.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
