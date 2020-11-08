package com.yuxianglw.controller;


import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysUser;
import com.yuxianglw.entity.dto.SysUserDTO;
import com.yuxianglw.service.SysUserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/yuxianglw/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/addUser")
    public  Result<?> addUser(@RequestBody SysUser sysUser){
        return sysUserService.addUser(sysUser);
    }

    @PutMapping("/editUser")
    public Result editUser(@RequestBody SysUserDTO sysUserDTO){
        return  sysUserService.editUser(sysUserDTO);
    }

    @PutMapping("/deleteUser")
    @RequiresRoles(value = {"admin"},logical = Logical.OR)
    public Result<?> deleteUser(@RequestBody SysUserDTO sysUserDTO){
        return sysUserService.deleteUserById(sysUserDTO);
    }

    @GetMapping("/queryUser")
    public Result<?> queryUser( @RequestParam(value = "username",required = false) String username,
                                @RequestParam(value = "phone",required = false) String phone,
                                @RequestParam(value = "pagenum") int pagenum,
                                @RequestParam(value = "pagesize") int pagesize){
        return  sysUserService.queryUser(username,phone,pagenum,pagesize);
    }

    @PutMapping("/changeStatus")
    public Result<?> editUserStatus(@RequestBody SysUserDTO sysUserDTO){
        return sysUserService.editUserStatus(sysUserDTO);
    }
    @PutMapping("/batchDeleteUser")
    public Result<?> batchDeleteUser(@RequestBody List<SysUserDTO> sysUserDTO){
        return sysUserService.batchDeleteUser(sysUserDTO);
    }
    @GetMapping("/queryUserInfo")
    public Result<?> queryUserInfo(){
        return sysUserService.queryUserInfo();
    }

}

