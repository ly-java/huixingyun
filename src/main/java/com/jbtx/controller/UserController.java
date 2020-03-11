package com.jbtx.controller;

import com.jbtx.common.ResponseBean;
import com.jbtx.entity.User;
import com.jbtx.enums.UnicomResponseEnums;
import com.jbtx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @file: UserController
 * @Description:
 * @Author: ls
 * @Date: 2019/12/14 22:56
 */
@Api(description = "用户管理控制器")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页获取中心人员信息", notes = "分页获取中心人员信息", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseBean<List<User>> getAllUser(
            @ApiParam(required = true, name = "currentPage", value = "当前页码") @RequestParam Integer currentPage,
            @ApiParam(required = true, name = "pageSize", value = "每页数量") @RequestParam Integer pageSize,
            @ApiParam(name = "username", value = "中心人员名字") @RequestParam(required = false) String username,
            @ApiParam(name = "roleid", value = "角色ID") @RequestParam(required = false) Long roleid
    ) {
        return new ResponseBean(true, userService.selectAll(currentPage, pageSize, username, roleid), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "判断该中心人员是否能删除的方法", notes = "判断该中心人员是否能删除的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/isDeleteUser", method = RequestMethod.POST)
    public ResponseBean isdelete(
            @ApiParam(required = true, name = "id", value = "要逻辑删除的主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, userService.isDelete(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "逻辑删除中心人员信息", notes = "逻辑删除中心人员信息", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public ResponseBean delete(
            @ApiParam(required = true, name = "id", value = "要逻辑删除的主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, userService.deleteUser(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "查询中心人员信息", notes = "根据主键id查询中心人员信息", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseBean<User> findById(
            @ApiParam(required = true, name = "id", value = "要查询的主键id") @RequestParam Long id
    ) {
        return new ResponseBean<User>(true, userService.selectById(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "新增或修改中心人员的方法", notes = "新增或修改中心人员的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/addOrUpdateUser", method = RequestMethod.POST)
    public ResponseBean addOrUpdateUser(
            @ApiParam(required = true, name = "user", value = "要进行操作的中心人员对象") @RequestBody User user
    ) {
        return new ResponseBean(true, userService.addOrUpdateUser(user), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "获取教师信息列表", notes = "获取教师信息列表", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public ResponseBean<List<User>> getUserList() {
        return new ResponseBean(true, userService.selectAllUser(), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "判断手机号账号是否存在", notes = "判断手机号是否可用，可用返回true，不可用返回false", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
    public ResponseBean checkPhone(
            @ApiParam(required = true, name = "userphone", value = "手机号") @RequestParam String userphone
    ) {
        return new ResponseBean(true, userService.selectByPhone(userphone), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "中心人员导出", notes = "中心人员导出，名字模糊查询，职位精确查询", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/export", method = {RequestMethod.GET})
    public void export(
            @ApiParam(name = "username", value = "中心人员名字") @RequestParam(required = false) String username,
            @ApiParam(name = "roleid", value = "职位ID") @RequestParam(required = false) Long roleid,
            HttpServletResponse response
    ) {
        userService.export(username, roleid, response);
    }
}
