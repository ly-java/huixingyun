package com.jbtx.dao;

import com.jbtx.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> seleteAll(@Param("username") String username, @Param("roleid") Long roleid, @Param("type") String type);

    /**
     * 根据中心人员主键id查询班级数量
     *
     * @param id
     * @return
     */
    int selectById(@Param("id") Long id);
}