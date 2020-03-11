package com.jbtx.controller;


import com.jbtx.common.ResponseBean;
import com.jbtx.entity.Subjects;
import com.jbtx.enums.UnicomResponseEnums;
import com.jbtx.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(description = "科目管理控制器")
@RestController
@RequestMapping(value = "/api/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "获取所有科目列表", notes = "获取所有科目列表", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseBean<List<Subjects>> getAllSubjects() {
        return new ResponseBean<List<Subjects>>(true, subjectService.getAll(), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "课程表增删改通用的方法", notes = "课程表增删改通用的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/updateSubject", method = RequestMethod.POST)
    public ResponseBean updateSubject(
            @ApiParam(required = true, name = "subjects", value = "科目对象") @RequestBody Subjects subjects
    ) {
        return new ResponseBean<>(true, subjectService.updateSubject(subjects), UnicomResponseEnums.CODE_002);
    }

    @ApiOperation(value = "逻辑删除课程", notes = "逻辑删除课程", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/deleteSubject", method = RequestMethod.POST)
    public ResponseBean deleteSubject(
            @ApiParam(required = true, name = "id", value = "课程id") @RequestParam Long id
    ) {
        return new ResponseBean(true, subjectService.delete(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "判断课程是否可以删除", notes = "判断课程是否可以删除", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/isDeleteSubject", method = RequestMethod.POST)
    public ResponseBean isdeleteSubject(
            @ApiParam(required = true, name = "id", value = "课程id") @RequestParam Long id
    ) {
        return new ResponseBean(true, subjectService.isdelete(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "判断课程名是否可用", notes = "判断课程名是否可用，true可用false不可用", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/checkSubjectName", method = RequestMethod.POST)
    public ResponseBean checkSubjectName(
            @ApiParam(required = true, name = "subjectname", value = "课程名称") @RequestParam String subjectname
    ) {
        return new ResponseBean(true, subjectService.checkSubjectName(subjectname), UnicomResponseEnums.CODE_200);
    }
}
