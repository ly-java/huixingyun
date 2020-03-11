package com.jbtx.controller;

import com.jbtx.common.ResponseBean;
import com.jbtx.enums.UnicomResponseEnums;
import com.jbtx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "登录管理控制器")
@RestController
@RequestMapping(value = "api/login")
public class LoginController {
    @Autowired
    UserService userService;
    @ApiOperation(value = "中心人员登录的方法", notes = "中心人员登录的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseBean login(
            @ApiParam(name = "userphone", value = "用户手机号") @RequestParam(required = false) String userphone,
            @ApiParam(name = "password", value = "用户密码") @RequestParam(required = false) String password
    ) {
        return new ResponseBean(true, userService.login(userphone, password), UnicomResponseEnums.CODE_200);
    }
}
