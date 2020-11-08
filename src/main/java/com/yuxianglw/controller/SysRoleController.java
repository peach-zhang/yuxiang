package com.yuxianglw.controller;


import com.yuxianglw.common.Result;
import com.yuxianglw.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统角色 前端控制器
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/yuxianglw/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/queryRoles")
    public Result<?> queryRoles(){
        return sysRoleService.queryRoles();
    }

    @GetMapping("/queryTheRolesTheUserHas")
    public Result<?> queryTheRolesTheUserHas(@RequestParam("userId") String userId){
        return sysRoleService.queryTheRolesTheUserHas(userId);
    }

}

