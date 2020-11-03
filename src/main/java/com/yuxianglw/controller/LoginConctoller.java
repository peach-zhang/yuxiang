package com.yuxianglw.controller;

import com.yuxianglw.common.Result;
import com.yuxianglw.entity.dto.LoginUser;
import com.yuxianglw.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        Map<String,String> data = sysUserService.login(loginUser.getUserName(), loginUser.getPassWord());
        return Result.ok(data);
    }
    @GetMapping("/logout")
    public Result<?> logout(){
        return sysUserService.logout();
    }

}
