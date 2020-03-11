package com.jbtx.controller;

import com.jbtx.common.ResponseBean;
import com.jbtx.entity.Roles;
import com.jbtx.enums.UnicomResponseEnums;
import com.jbtx.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "角色管理控制器")
@RestController
@RequestMapping(value = "/api/role")
public class RolesController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取所有角色信息列表", notes = "获取所有角色信息列表", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseBean<List<Roles>> getAllUser(
            @ApiParam(required = true, name = "rolename", value = "角色名称") @RequestParam(required = false) String rolename
    ) {
        return new ResponseBean<List<Roles>>(true, roleService.getRoles(rolename));
    }

    @ApiOperation(value = "增加角色的方法", notes = "增加角色的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public ResponseBean addRoles(
            @ApiParam(required = true, name = "roles", value = "角色对象") @RequestBody Roles roles
    ) {
        return new ResponseBean(true, roleService.updateRoles(roles), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "删除角色的方法", notes = "删除角色的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    public ResponseBean deleteRole(
            @ApiParam(required = true, name = "id", value = "角色主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, roleService.delete(id), UnicomResponseEnums.CODE_200);
    }

    @ApiOperation(value = "判断角色是否可以删除的方法", notes = "判断角色是否可以删除的方法", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/isDeleteRole", method = RequestMethod.POST)
    public ResponseBean isDelete(
            @ApiParam(required = true, name = "id", value = "角色主键id") @RequestParam Long id
    ) {
        return new ResponseBean(true, roleService.isDelete(id), UnicomResponseEnums.CODE_200);
    }

}
