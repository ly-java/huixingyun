package com.jbtx.dao;

import com.jbtx.entity.Files;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface FilesMapper extends BaseMapper<Files> {
    /**
     * 根据文件名模糊查询学生信息
     *
     * @param filename
     * @return
     */
    List<Files> selectAllFiles(@Param("filename") String filename);
}