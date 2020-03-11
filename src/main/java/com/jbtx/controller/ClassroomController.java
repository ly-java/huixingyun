package com.jbtx.controller;

import com.jbtx.common.ResponseBean;
import com.jbtx.entity.ClassRoom;
import com.jbtx.enums.UnicomResponseEnums;
import com.jbtx.service.ClassroomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "班级管理控制器")
@RestController
@RequestMapping(value = "/api/classroom")
public class ClassroomController {
    @Autowired
    private ClassroomService classroomService;

    @ApiOperation(value = "获取班级列表", notes = "根据条件查询获取班级列表", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseBean getClassroomList(
            @ApiParam(required = true, name = "username", value = "教师姓名") @RequestParam(required = false) String username,
            @ApiParam(required = true, name = "subjectid", value = "课程id") @RequestParam(required = false) Long subjectid
    ) {
        return new ResponseBean(true, classroomService.getAll(username, subjectid), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "新增班级的方法", notes = "新增班级的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/addClass", method = RequestMethod.POST)
    public ResponseBean addClass(
            @ApiParam(required = true, name = "classroom", value = "班级对象") @RequestBody ClassRoom classRoom
    ) {
        return new ResponseBean(true, classroomService.addClass(classRoom), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "删除班级的方法", notes = "删除班级的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseBean deleteClass(
            @ApiParam(required = true, name = "id", value = "班级主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, classroomService.deleteClass(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "删除班级的方法", notes = "删除班级的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/isDelete", method = RequestMethod.POST)
    public ResponseBean isDeleteClass(
            @ApiParam(required = true, name = "id", value = "班级主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, classroomService.isdeleteClass(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "验证班级名是否存在", notes = "验证班级名是否存在的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/checkName", method = RequestMethod.POST)
    public ResponseBean checkName(
            @ApiParam(required = true, name = "classname", value = "班级名称") @RequestParam String classname
    ) {
        return new ResponseBean(true, classroomService.checkName(classname), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "根据课程id查找班级", notes = "根据课程id查找班级", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getClassBySubjectId", method = RequestMethod.POST)
    public ResponseBean getClassBySubjectid(
            @ApiParam(required = true, name = "subjectid", value = "课程id") @RequestParam Long subjectid
    ) {
        return new ResponseBean(true, classroomService.getAllClassBySubjectId(subjectid), UnicomResponseEnums.CODE_200);
    }


}
