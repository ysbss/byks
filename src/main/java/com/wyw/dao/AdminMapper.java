package com.wyw.dao;

import com.wyw.pojo.Admin;
import org.springframework.stereotype.Repository;

/**
 * @author WYW
 */
@Repository
public interface AdminMapper {
    /**
     * @param aId  管理员id主键
     * @author WYW
     * 主要用于管理员登陆时，在数据库进行查询是否有这个用户有则进入页面，没有则返回错误
     *
     * */
    Admin fetchAdminById(Long aId);

}
