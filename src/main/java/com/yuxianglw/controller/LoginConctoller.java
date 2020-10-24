package com.yuxianglw.controller;

import com.yuxianglw.common.Result;
import com.yuxianglw.entity.dto.LoginUser;
import com.yuxianglw.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private SysUserService SysUserService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginUser loginUser){
        String token = SysUserService.login(loginUser.getUserName(), loginUser.getPassWord());
        return Result.ok((Object)token);
    }
    @PostMapping("/loginout")
    public Result<?> loginout(@RequestBody LoginUser loginUser){
        String token = SysUserService.login(loginUser.getUserName(), loginUser.getPassWord());
        return Result.ok((Object)token);
    }

}
