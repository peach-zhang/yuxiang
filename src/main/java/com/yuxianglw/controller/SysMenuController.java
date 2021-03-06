package com.yuxianglw.controller;


import com.yuxianglw.common.Result;
import com.yuxianglw.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 菜单  前端控制器
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-25
 */
@RestController
@RequestMapping("/yuxianglw/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/menu")
    public Result<?> getMenu(){
        return sysMenuService.queryMenuByUser();
    }
}

