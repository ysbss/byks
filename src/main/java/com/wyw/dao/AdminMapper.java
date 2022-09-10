package com.wyw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyw.pojo.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author WYW
 */
@Repository
public interface AdminMapper  {
    /**
     * 主要用于管理员登陆时，在数据库进行查询是否有这个用户有则进入页面，没有则返回错误
     * @param aId  管理员id主键
     * @author WYW
     * @return admin
     * */
    Admin fetchAdminById(Long aId);

    /**
     * 增加管理员
     * @param admin
     * @return int
     * */
    int addAdmin(Map<String,Object> admin);

    /**
     * 更新管理员
     * @param admin
     * @return int
     * */
    int updateAdmin(Map<String,Object> admin);

    /**
     * 查询所有管理员
     * @param admin
     * @return admin
     * */
    List<Admin> fetchAdminsList(Map<String,Object> admin);

    /**
     * 查询存在的管理员
     * @param findExistAdmin
     * @return admin
     * */
    Admin findExistAdmin(String findExistAdmin);
}
