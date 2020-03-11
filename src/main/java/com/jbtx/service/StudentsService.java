package com.jbtx.service;

import com.github.pagehelper.PageInfo;
import com.jbtx.entity.Students;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface StudentsService {
    /**
     * 添加或修改学生的方法
     *
     * @param students 学生对象
     * @return 999：添加成功 888：添加失败 777：修改成功 666:修改失败
     */
    int addOrUpdateStudents(Students students);

    /**
     * 逻辑删除学生的方法
     *
     * @param id 学生id
     * @return 999：删除成功 888：删除失败
     */
    int deleteStudents(Long id);

    /**
     * 判断学生对否可以删除的方法
     *
     * @param id 主键id
     * @return true 可以删除 false不可以删除
     */
    boolean isdelete(Long id);

    /**
     * 根据家长id获取孩子列表
     *
     * @param parentsid 家长id
     * @return 孩子列表
     */
    List<Students> getChildren(Long parentsid);

    /**
     * 根据学生姓名和教师id分页查找学生
     *
     * @param currentPage  当前页
     * @param pageSize     显示条数
     * @param studentsname 学生姓名（模糊查询）
     * @param teacherid    教师id（精准查询）
     * @param classid      班级id（精准查询）
     * @return 学生列表
     */
    PageInfo<Students> getStudents(Integer currentPage, Integer pageSize, String studentsname, Long teacherid, Long classid);

    /**
     * 根据学生id获取学生信息
     *
     * @param id 主键id
     * @return 学生对象
     */
    Students selectById(Long id);

    /**
     * 导出学生列表
     *
     * @param studentsname
     * @param teacherid
     * @param classid
     * @param response
     */
    int export(String studentsname, Long teacherid, Long classid, HttpServletResponse response);
}