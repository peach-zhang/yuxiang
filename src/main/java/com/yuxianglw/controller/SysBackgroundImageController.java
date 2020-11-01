package com.yuxianglw.controller;


import com.yuxianglw.common.Result;
import com.yuxianglw.service.SysBackgroundImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 登录背景图片  前端控制器
 * </p>
 *
 * @author zhangtao
 * @since 2020-11-01
 */
@RestController
@RequestMapping("/yuxianglw/sysBackgroundImage")
public class SysBackgroundImageController {

    @Autowired
    private SysBackgroundImageService sysBackgroundImageService;

    @GetMapping("/getBackgroundImage")
    public Result<?> getBackgroundImage(){
        return sysBackgroundImageService.getBackgroundImage();
    }

}

