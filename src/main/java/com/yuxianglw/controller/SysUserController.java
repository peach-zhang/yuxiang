package com.yuxianglw.controller;


import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysUser;
import com.yuxianglw.entity.dto.SysUserDTO;
import com.yuxianglw.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public  String addUser(@RequestBody SysUser sysUser){
        boolean save = sysUserService.save(sysUser);
        return  save?"保存成功":"保存失败";
    }

    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody SysUser sysUser){
        boolean save = sysUserService.updateById(sysUser);
        return  save?Result.ok("更新成功"):Result.error("保存失败");
    }

    @DeleteMapping("/deleteUser")
    public Result<?> deleteUser(@RequestParam("id") String id){
        boolean result = sysUserService.removeById(id);
        String msg = result?"删除成功":"删除失败";
        return Result.ok(msg);
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

}

