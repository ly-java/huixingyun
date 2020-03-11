package com.jbtx.controller;

import com.jbtx.common.ResponseBean;
import com.jbtx.enums.UnicomResponseEnums;
import com.jbtx.service.FileService;
import com.jbtx.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Api(description = "文件上传控制器")
@RestController
@RequestMapping(value = "api/upload")
public class UploadController {
    @Autowired
    private PictureService pictureService;
    @Autowired
    private FileService fileService;


    @ApiOperation(value = "上传文件", notes = "上传文件", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/addFiles", method = RequestMethod.PUT)
    public ResponseBean saveFile(
            @ApiParam(required = true, name = "files", value = "文件") @RequestParam(name = "files") MultipartFile files,
            @ApiParam(required = true, name = "id", value = "学生id") @RequestParam Long id,
            @ApiParam(required = true, name = "name", value = "文件名") @RequestParam String name
    ) {
        return new ResponseBean(true, fileService.upload(id, name, files), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "上传图片", notes = "上传图片", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/addPicture", method = RequestMethod.PUT)
    public ResponseBean savePicture(
            @ApiParam(required = true, name = "picture1", value = "图片") @RequestParam(required = false) MultipartFile picture1,
            @ApiParam(name = "picture2", value = "图片2") @RequestParam(required = false) MultipartFile picture2,
            @ApiParam(required = true, name = "id", value = "学生id") @RequestParam Long id,
            @ApiParam(required = true, name = "picturedescribe", value = "图片描述") @RequestParam String picturedescribe
    ) {
        boolean result = false;
        if (picture1 != null) {
            result = pictureService.addPicture(id, picturedescribe, picture1);
        }
        if (picture2 != null) {
            result = pictureService.addPicture(id, picturedescribe, picture2);
        }
        return new ResponseBean(true, result, UnicomResponseEnums.CODE_200);
    }
}
