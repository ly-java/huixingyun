package com.jbtx.dao;

import com.jbtx.entity.Parents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface ParentsMapper extends BaseMapper<Parents> {
    List<Parents> selectAllParents(@Param("studentsname") String studentsname);

    /**
     * 根据家长id查询学生数量
     *
     * @param id
     * @return
     */
    int countById(@Param("id") Long id);
}