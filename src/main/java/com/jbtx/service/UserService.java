package com.jbtx.service;

import com.github.pagehelper.PageInfo;
import com.jbtx.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @file: UserService
 * @Description:
 * @Author: ls
 * @Date: 2019/12/14 22:52
 */
public interface UserService {
    PageInfo<User> selectAll(Integer currentPage, Integer pageSize, String username, Long roleid);

    /**
     * 添加中心
     *
     * @param user
     * @return
     */
    int addOrUpdateUser(User user);

    /**
     * 逻辑删除
     *
     * @param id
     * @return 删除成功返回true，删除失败返回false
     */
    boolean deleteUser(Long id);

    /**
     * 根据id查询中心人员
     *
     * @param id
     * @return
     */
    User selectById(Long id);

    List<User> selectAllUser();

    boolean selectByPhone(String userphone);

    /**
     * 导出中心人员列表
     *
     * @param username
     * @param roleid
     * @param response
     */
    int export(String username, Long roleid, HttpServletResponse response);

    /**
     * 根据主键id判断该中心人员是否能删除
     *
     * @param id 中心人员主键id
     * @return 可以删除返回true ，不可以删除返回false
     */
    boolean isDelete(Long id);

    /**
     * @param phone
     * @param password
     * @return
     */
    boolean login(String phone, String password);
}

