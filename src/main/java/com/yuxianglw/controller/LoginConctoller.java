package com.yuxianglw.controller;

import com.yuxianglw.common.Result;
import com.yuxianglw.entity.dto.LoginUser;
import com.yuxianglw.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 登录接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/yuxianglw")
public class LoginConctoller {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginUser loginUser){
        String token = sysUserService.login(loginUser.getUserName(), loginUser.getPassWord());
        return Result.ok((Object)token);
    }
    @GetMapping("/logout")
    public Result<?> logout(){
        return sysUserService.logout();
    }

}
