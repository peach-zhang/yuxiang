package com.yuxianglw.controller;


import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysBackgroundImage;
import com.yuxianglw.service.SysBackgroundImageService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<SysBackgroundImage> backgroundImageList = sysBackgroundImageService.getBackgroundImageList();
        if(CollectionUtils.isNotEmpty(backgroundImageList)){
            int index= (int) (Math.random()* backgroundImageList.size());
            SysBackgroundImage sysBackgroundImage = backgroundImageList.get(index);
            return Result.ok(sysBackgroundImage);
        }
        return Result.ok();
    }

}

