package com.wyw.dao;

import com.wyw.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author WYW
 */
@Repository
public interface StudentMapper {


    /**
     * 主要用于学生登陆时，在数据库进行查询是否有这个用户有则进入页面，没有则返回错误
     * @param sId  学生id主键
     * @author WYW
     * @return student
     *
     * */
    Student fetchStuById(Long sId);

    /**
     * 获取某个学生的某个文件
     * @param sIdAndFpId
     * @return map
     * */
    Map<String,Object> fetchStuWithResumeById(Map<String,Object> sIdAndFpId);


    /**
     * 获取某个学生的所有简历
     * @param sId
     * @return list
     * */
    List<Map<String,Object>> fetchStuWithResumesList(Long sId);


    /**
     * 增加学生
     * @param student
     * @return int
     * */
    int addStudent(Map<String,Object> student);

    /**
     * 修改学生
     * @param student
     * @return int
     * */
    int updateStudent(Map<String,Object> student);

    /**
     * 获得所有学生
     * @param student
     * @return list
     * */
    List<Student> fetchStusList(Map<String,Object> student);
}
