package com.jbtx.service;

import com.jbtx.entity.Roles;

import java.util.List;

public interface RoleService {
    /**
     * 根据角色名称查询角色列表
     *
     * @param rolename
     * @return
     */
    List<Roles> getRoles(String rolename);

    /**
     * 增加或删除角色的方法
     *
     * @param roles 角色对象
     * @return 执行结果 999：新增成功 888：新增失败 777：删除成功 666：删除失败
     */
    int updateRoles(Roles roles);

    /**
     * 删除角色的方法
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 判断角色是否能删除的方法
     *
     * @param id
     * @return
     */
    boolean isDelete(Long id);
}
