package com.jbtx.dao;

import com.jbtx.entity.Students;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface StudentsMapper extends BaseMapper<Students> {

    int insertSelective(Students record);

    Students selectById(Long id);

    List<Students> getChildren(@Param("parentsid") Long parentsid);

    List<Students> getStudents(@Param("studentsname") String studentsname, @Param("teacherid") Long teacherid, @Param("classid") Long classid);

}