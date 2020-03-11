package com.jbtx.service;

import com.github.pagehelper.PageInfo;
import com.jbtx.entity.Parents;

import javax.servlet.http.HttpServletResponse;


public interface ParentsService {
    /**
     * 根据学生名字查询所有家长信息
     *
     * @param currentPage  当前页
     * @param pageSize     显示条数
     * @param studentsname 学生名字
     * @return
     */
    PageInfo<Parents> getAllParents(Integer currentPage, Integer pageSize, String studentsname);

    /**
     * 判断该家长是否能删
     *
     * @param id 家长id
     * @return 可以删除返回true，不可以删除返回false
     */
    boolean isDelete(Long id);

    /**
     * 根据家长主键删除家长的方法
     *
     * @param id 家长id
     * @return 删除成功返回true，删除失败返回false
     */
    boolean delete(Long id);

    /**
     * 添加或修改家长的方法
     *
     * @param parents 家长对象
     * @return 执行结果 999：添加成功 888：添加失败 777：修改成功 666：修改失败
     */
    Long addOrUpdateParents(Parents parents);

    /**
     * 根据手机号判断该用户是否可用
     *
     * @param parentsphone 手机号
     * @return 返回结果 true 可用 false 不可用
     */
    boolean checkPhone(String parentsphone);

    /**
     * 根据主键id查询家长信息
     *
     * @param id 家长主键id
     * @return 家长对象
     */
    Parents selectByid(Long id);

    /**
     * 家长信息导出
     *
     * @param studentsname
     */
    int export(String studentsname, HttpServletResponse response);

    /**
     * 跟据手机号判断该用户是否存在
     *
     * @param phone
     * @return
     */
    boolean isExist(String phone);

    /**
     * @param phone
     * @param password
     * @return
     */
    boolean login(String phone, String password);

}
