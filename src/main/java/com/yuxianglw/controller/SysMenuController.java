package com.yuxianglw.controller;


import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysMenu;
import com.yuxianglw.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/yuxianglw/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/menu")
    @Cacheable(value = "menu")
    public Result<?> getMenu(){
        List<SysMenu> menu = sysMenuService.list();
        return Result.ok(menu);
    }
}

