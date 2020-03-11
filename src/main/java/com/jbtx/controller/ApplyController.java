package com.jbtx.controller;

import com.jbtx.common.ResponseBean;
import com.jbtx.entity.Apply;
import com.jbtx.enums.UnicomResponseEnums;
import com.jbtx.service.ApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @file: ApplyController
 * @Description:
 * @Author: ls
 * @Date: 2019/12/14 13:41
 */
@Api(description = "申请信息管理控制器")
@RestController
@RequestMapping(value = "/other/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    @ApiOperation(value = "申请", notes = "申请", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ResponseBean apply(@RequestBody Apply apply) {
        return new ResponseBean(true, applyService.applyMes(apply), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "分页获取申请信息", notes = "分页获取申请信息", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getApplyList", method = RequestMethod.GET)
    public ResponseBean getApplyList(
            @ApiParam(required = true, name = "currentPage", value = "当前页码") @RequestParam Integer currentPage,
            @ApiParam(required = true, name = "pageSize", value = "每页数量") @RequestParam Integer pageSize,
            @ApiParam(required = true, name = "type", value = "申请类型（1-项目合作，2-实验申请，3-数据申请,4-住宿申请，5-车辆准入）") @RequestParam Integer type

    ) {
        return new ResponseBean(true, applyService.queryApplyList(currentPage, pageSize, type), UnicomResponseEnums.CODE_200);
    }


}
