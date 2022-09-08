package com.wyw.service;

import com.alibaba.fastjson.JSON;
import com.wyw.dao.AdminMapper;
import com.wyw.pojo.Admin;
import com.wyw.utils.FinalStaticValue;
import com.wyw.utils.RedisUtils;
import com.wyw.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 鱼酥不是叔
 */
@Service("AdminServiceImpl")
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Resource
    RedisUtils redisUtils;


    @Override
    public Admin fetchAdminById(Long aId) {
        if (redisUtils.hget(FinalStaticValue.TABLE_ADMIN, aId.toString()) == null){
            System.out.println("我没在redis缓存里面555");
//            redisUtils.set(FinalStaticValue.TABLE_ADMIN+aId.toString(), adminMapper.fetchAdminById(aId),3600);
            redisUtils.hset(FinalStaticValue.TABLE_ADMIN,aId.toString(), adminMapper.fetchAdminById(aId),3600);
//            return adminMapper.fetchAdminById(aId);
        }
//        return adminMapper.fetchAdminById(aId);
        System.out.println("我在redis缓存里哈哈哈哈");
        System.out.println(redisUtils.hget(FinalStaticValue.TABLE_ADMIN,FinalStaticValue.TABLE_ADMIN + aId.toString()));
        return JSON.parseObject(JSON.toJSONString(redisUtils.hget(FinalStaticValue.TABLE_ADMIN, aId.toString())),Admin.class);
//        return (Admin) redisUtils.hget(FinalStaticValue.TABLE_ADMIN,FinalStaticValue.TABLE_ADMIN + aId.toString());


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
