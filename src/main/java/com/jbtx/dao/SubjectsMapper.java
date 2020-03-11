package com.jbtx.dao;

import com.jbtx.entity.Subjects;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface SubjectsMapper extends BaseMapper<Subjects> {
    /**
     * 根据课程id统计班级数量
     *
     * @param id
     * @return
     */
    int countById(@Param("id") Long id);
}