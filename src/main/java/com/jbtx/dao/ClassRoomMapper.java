package com.jbtx.dao;

import com.jbtx.entity.ClassRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface ClassRoomMapper extends BaseMapper<ClassRoom> {
    /**
     * 根据条件查询班级列表
     *
     * @param username
     * @param subjectid
     * @return
     */
    List<ClassRoom> selectAllClassRoom(@Param("username") String username, @Param("subjectid") Long subjectid);

    ClassRoom selectById(@Param("id") Long id);

    /**
     * 根据班级id统计学生总数
     * @param id
     * @return
     */
    int countById(@Param("id") Long id);
}