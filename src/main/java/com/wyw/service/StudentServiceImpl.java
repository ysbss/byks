package com.wyw.service;


import com.wyw.dao.StudentMapper;
import com.wyw.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author WYW
 */
@Service("StudentsServiceImpl")
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public Student fetchStuById(Long sId) {
        return studentMapper.fetchStuById(sId);
    }

    @Override
    public Student fetchExistStudent(String fetchExistStudent) {
        return studentMapper.fetchExistStudent(fetchExistStudent);
    }

    @Override
    public Map<String, Object> fetchStuWithResumeById(Map<String,Object> sIdAndFpId) {
        return studentMapper.fetchStuWithResumeById(sIdAndFpId);
    }

    @Override
    public List<Map<String, Object>> fetchStuWithResumesList(Long sId) {
        return studentMapper.fetchStuWithResumesList(sId);
    }

    @Override
    public int addStudent(Map<String, Object> student) {
        return studentMapper.addStudent(student);
    }

    @Override
    public int updateStudent(Map<String, Object> student) {
        return studentMapper.updateStudent(student);
    }

    @Override
    public List<Student> fetchStusList(Map<String, Object> student) {
        return studentMapper.fetchStusList(student);
    }
}
