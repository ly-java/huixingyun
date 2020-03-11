package com.jbtx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbtx.dao.ApplyMapper;
import com.jbtx.entity.Apply;
import com.jbtx.exception.ValidationException;
import com.jbtx.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @file: ApplyServiceImpl
 * @Description:
 * @Author: ls
 * @Date: 2019/12/14 13:31
 */
@Service
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public boolean applyMes(Apply apply) {
        if(apply == null){
            throw new ValidationException("申请信息不能为空");
        }
        apply.setCreateTime(new Date());
        return applyMapper.insertSelective(apply) > 0;
    }

    @Override
    public PageInfo<Apply> queryApplyList(Integer currentPage, Integer pageSize, Integer type) {
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo<>(applyMapper.queryApplyByType(type));
    }
}
