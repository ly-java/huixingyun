package com.jbtx.controller;

import com.jbtx.common.ResponseBean;
import com.jbtx.enums.UnicomResponseEnums;
import com.jbtx.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(description = "文件管理控制器")
@RestController
@RequestMapping(value = "api/files")
public class FilesController {
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "获取所有文件列表", notes = "根据文件名获取所有文件列表", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseBean getAllFiles(
            @ApiParam(required = true, name = "currentPage", value = "当前页") @RequestParam Integer currentPage,
            @ApiParam(required = true, name = "pageSize", value = "显示条数") @RequestParam Integer pageSize,
            @ApiParam(name = "filename", value = "文件名") @RequestParam(required = false) String filename
    ) {
        return new ResponseBean(true, fileService.getAllFiles(currentPage, pageSize, filename));
    }

    @ApiOperation(value = "逻辑删除文件的方法", notes = "根据主键ID逻辑删除文件的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseBean Delete(
            @ApiParam(required = true, name = "id", value = "文件主键ID") @RequestParam Long id
    ) {
        return new ResponseBean(true, fileService.isDelete(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "根据主键ID下载文件的方法", notes = "根据主键ID下载文件的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/downFiles", method = RequestMethod.GET)
    public void downFiles(
            @ApiParam(required = true, name = "id", value = "文件主键ID") @RequestParam Long id,
            HttpServletResponse response
    ) {
        fileService.downById(id, response);
    }
}
