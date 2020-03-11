package com.jbtx.controller;

import com.github.pagehelper.PageInfo;
import com.jbtx.common.ResponseBean;
import com.jbtx.entity.Parents;
import com.jbtx.enums.UnicomResponseEnums;
import com.jbtx.service.ParentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;


@Api(description = "家长管理控制器")
@RestController
@RequestMapping(value = "/api/parents")
public class ParentsController {
    @Autowired
    private ParentsService parentsService;

    @ApiOperation(value = "分页获取家长列表的方法", notes = "根据学生姓名分页获取家长列表的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseBean<PageInfo<Parents>> getParentsList(
            @ApiParam(required = true, name = "currentPage", value = "当前页码") @RequestParam Integer currentPage,
            @ApiParam(required = true, name = "pageSize", value = "每页数量") @RequestParam Integer pageSize,
            @ApiParam(required = true, name = "studentsname", value = "学生姓名") @RequestParam(required = false) String studentsname
    ) {
        return new ResponseBean<>(true, parentsService.getAllParents(currentPage, pageSize, studentsname), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "根据主键id删除家长的方法", notes = "根据主键id逻辑删除家长的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/deleteParents", method = RequestMethod.POST)
    public ResponseBean delete(
            @ApiParam(required = true, name = "id", value = "家长主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, parentsService.delete(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "根据主键id判断家长能否删除的方法", notes = "根据主键id判断家长能否删除的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/isDeleteParents", method = RequestMethod.POST)
    public ResponseBean isDelete(
            @ApiParam(required = true, name = "id", value = "家长主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, parentsService.isDelete(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "添加或修改家长的方法", notes = "添加家长的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/addOrUpdateParents", method = RequestMethod.POST)
    public ResponseBean addParents(
            @ApiParam(required = true, name = "parents", value = "要添加的家长对象") @RequestBody Parents parents
    ) {
        return new ResponseBean(true, parentsService.addOrUpdateParents(parents), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "检查该手机号是否可用", notes = "检查该手机号是否可用，true可用，false不可用", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
    public ResponseBean checkPhone(
            @ApiParam(required = true, name = "parentsphone", value = "要验证的手机号") @RequestParam String parentsphone
    ) {
        return new ResponseBean(true, parentsService.checkPhone(parentsphone), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "根据主键id查找家长的方法", notes = "根据主键id查找家长的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/selectParents", method = RequestMethod.POST)
    public ResponseBean select(
            @ApiParam(required = true, name = "id", value = "家长主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, parentsService.selectByid(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "家长信息导出", notes = "家长信息导出，学生名字模糊查询", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/export", method = {RequestMethod.GET})
    public void export(
            @ApiParam(name = "studentsname", value = "学生名字") @RequestParam(required = false) String studentsname,
            HttpServletResponse response
    ) {
        parentsService.export(studentsname, response);
    }
}
