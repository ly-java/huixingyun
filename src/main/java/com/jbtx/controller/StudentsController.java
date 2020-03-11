package com.jbtx.controller;

import com.jbtx.common.ResponseBean;
import com.jbtx.entity.Students;
import com.jbtx.enums.UnicomResponseEnums;
import com.jbtx.service.StudentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api(description = "学生控制管理器")
@RestController
@RequestMapping(value = "/api/students")
public class StudentsController {
    @Autowired
    StudentsService studentsService;

    @ApiOperation(value = "添加或修改学生的方法", notes = "添加或修改学生的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "addOrUpdateStudents", method = RequestMethod.POST)
    public ResponseBean addOrUpdate(
            @ApiParam(required = true, name = "students", value = "学生对象") @RequestBody Students students
    ) {
        return new ResponseBean(true, studentsService.addOrUpdateStudents(students), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "逻辑删除学生的方法", notes = "根据主键id逻辑删除学生的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/deleteStudents", method = RequestMethod.POST)
    public ResponseBean delete(
            @ApiParam(required = true, name = "id", value = "学生主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, studentsService.deleteStudents(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "判断该学生是否能删除的方法", notes = "根据主键id判断该学生是否能删除的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/isdelete", method = RequestMethod.POST)
    public ResponseBean isdelete(
            @ApiParam(required = true, name = "id", value = "学生主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, studentsService.isdelete(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "获取孩子列表的方法", notes = "根据家长id获取孩子列表的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getChildren", method = RequestMethod.POST)
    public ResponseBean getChildren(
            @ApiParam(required = true, name = "id", value = "家长主键id") @RequestParam Long parentsid
    ) {
        return new ResponseBean(true, studentsService.getChildren(parentsid), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "分页获取学生列表的方法", notes = "根据学生姓名（模糊查询），教师id（精确查询）分页获取孩子列表的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseBean getStudents(
            @ApiParam(required = true, name = "currentPage", value = "当前页") @RequestParam Integer currentPage,
            @ApiParam(required = true, name = "pageSize", value = "显示条数") @RequestParam Integer pageSize,
            @ApiParam(required = true, name = "studentsname", value = "学生姓名") @RequestParam(required = false) String studentsname,
            @ApiParam(required = true, name = "teacherid", value = "教师主键id") @RequestParam(required = false) Long teacherid,
            @ApiParam(required = true, name = "classid", value = "班级主键id") @RequestParam(required = false) Long classid
    ) {
        return new ResponseBean(true, studentsService.getStudents(currentPage, pageSize, studentsname, teacherid, classid), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "获取学生信息的方法", notes = "根据主键id获学生的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getStudent", method = RequestMethod.POST)
    public ResponseBean getStudent(
            @ApiParam(required = true, name = "id", value = "学生主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, studentsService.selectById(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "学生导出", notes = "学生导出，名字模糊查询，代课老师精确查询", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/export", method = {RequestMethod.GET})
    public void export(
            @ApiParam(name = "studentsname", value = "学生名字") @RequestParam(required = false) String studentsname,
            @ApiParam(name = "teacherid", value = "老师ID") @RequestParam(required = false) Long teacherid,
            @ApiParam(name = "classid", value = "班级ID") @RequestParam(required = false) Long classid,
            HttpServletResponse response
    ) {
        studentsService.export(studentsname, teacherid, classid, response);
    }
}
