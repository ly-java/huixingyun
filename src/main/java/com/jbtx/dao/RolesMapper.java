package com.jbtx.dao;

import com.jbtx.entity.Roles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface RolesMapper extends BaseMapper<Roles> {
    List<Roles> getRoles(@Param("rolename") String rolename);

    /**
     * 根据角色id查询该角色下有多少人
     *
     * @param id
     * @return
     */
    int selectById(Long id);
}