package com.yuxianglw.controller;

import com.yuxianglw.entity.dto.LoginUser;
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

    @PostMapping("/login")
    public void login(@RequestBody LoginUser loginUser){

    }
}
