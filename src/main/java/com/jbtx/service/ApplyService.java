package com.jbtx.service;

import com.github.pagehelper.PageInfo;
import com.jbtx.entity.Apply;

/**
 * @file: ApplyService
 * @Description:
 * @Author: ls
 * @Date: 2019/12/14 13:31
 */
public interface ApplyService {

    boolean applyMes(Apply apply);

    /**
     * 分页获取所有申请信息
     *
     * @param currentPage 当前页码
     * @param pageSize    每页数量
     * @param type        申请类型（1-项目合作，2-实验申请，3-数据申请）
     * @return
     */
    PageInfo<Apply> queryApplyList(Integer currentPage, Integer pageSize, Integer type);
}
