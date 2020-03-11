package com.jbtx.service;

import com.jbtx.entity.ClassRoom;

import java.util.List;

public interface ClassroomService {
    /**
     * 根据教师姓名和课程id显示班级列表
     *
     * @param username
     * @param subjectid
     * @return
     */
    List<ClassRoom> getAll(String username, Long subjectid);

    /**
     * 添加班级的方法
     *
     * @param classRoom 班级对象
     * @return 执行结果 777：添加成功 666：添加失败
     */
    int addClass(ClassRoom classRoom);

    /**
     * 删除班级的方法
     *
     * @param id 班级id
     * @return 删除成功返回true，删除失败返回false
     */
    boolean deleteClass(Long id);

    /**
     * 判断班级是否可以删除的方法
     *
     * @param id 班级id
     * @return 可以删除返回true，不可以删除返回false
     */
    boolean isdeleteClass(Long id);

    /**
     * 根据班级名字查询班级信息
     *
     * @param classname
     * @return
     */
    boolean checkName(String classname);

    /**
     * 根据课程id返回班级列表
     *
     * @param subjectid
     * @return
     */
    List<ClassRoom> getAllClassBySubjectId(Long subjectid);
}
