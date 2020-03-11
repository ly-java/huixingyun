package com.jbtx.service.impl;

import com.jbtx.dao.RolesMapper;
import com.jbtx.entity.Roles;
import com.jbtx.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RolesServiceImpl implements RoleService {
    @Autowired
    private RolesMapper rolesMapper;

    /**
     * 根据角色名称查询角色列表的方法
     *
     * @param rolename
     * @return
     */
    @Override
    public List<Roles> getRoles(String rolename) {
        List<Roles> list = rolesMapper.getRoles(rolename);
        return list;
    }

    /**
     * 新增角色信息的方法
     *
     * @param roles 角色对象
     * @return
     */
    @Override
    public int updateRoles(Roles roles) {
        roles.setCreateid(1L);
        roles.setCreatetime(new Date());
        roles.setIsdelete("1");
        return rolesMapper.insertSelective(roles) == 1 ? 999 : 888;
    }

    @Override
    public boolean isDelete(Long id) {
        return rolesMapper.selectById(id) == 0;
    }

    @Override
    public boolean delete(Long id) {
        Roles roles = new Roles();
        roles.setId(id);
        roles.setIsdelete("2");
        roles.setUpdateid(1L);
        roles.setUpdatetime(new Date());
        return rolesMapper.updateByPrimaryKeySelective(roles) == 1;
    }
}
