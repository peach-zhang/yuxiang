package com.yuxianglw.controller;


import com.yuxianglw.entity.SysUser;
import com.yuxianglw.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String updateUser(@RequestBody SysUser sysUser){
        boolean save = sysUserService.updateById(sysUser);
        return  save?"保存成功":"保存失败";
    }

    @PutMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") String id){
        boolean save = sysUserService.removeById(id);
        return  save?"删除成功":"删除失败";
    }

}

