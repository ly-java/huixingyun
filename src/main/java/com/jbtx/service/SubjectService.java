package com.jbtx.service;

import com.jbtx.entity.Subjects;

import java.util.List;

public interface SubjectService {
    List<Subjects> getAll();

    Subjects getById(Long id);

    int updateSubject(Subjects subjects);

    /**
     * 根据主键id判断该课程是否可以删除
     *
     * @param id 课程id
     * @return 可以删除返回true，不可以删除返回false
     */
    boolean isdelete(Long id);

    /**
     * 根据主键id删除课程
     *
     * @param id 课程id
     * @return 删除成功返回true，删除失败返回false
     */
    boolean delete(Long id);

    /**
     * 判断课程名是否可用
     *
     * @param subjectname 课程名
     * @return true可用 fasle不可用
     */
    boolean checkSubjectName(String subjectname);

}
