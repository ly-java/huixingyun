package com.jbtx.dao;

import com.jbtx.entity.Apply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface ApplyMapper extends BaseMapper<Apply> {

    List<Apply> queryApplyByType(@Param("type") Integer type);
}