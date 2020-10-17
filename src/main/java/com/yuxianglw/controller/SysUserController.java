package com.yuxianglw.controller;


import com.yuxianglw.entity.SysUser;
import com.yuxianglw.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-17
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/addUser")
    public  String addUser(@RequestBody SysUser sysUser){
        boolean save = sysUserService.save(sysUser);
        return  save?"保存成功":"保存失败";
    }

}

