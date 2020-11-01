package com.yuxianglw.service;

import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysBackgroundImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 登录背景图片  服务类
 * </p>
 *
 * @author zhangtao
 * @since 2020-11-01
 */
public interface SysBackgroundImageService extends IService<SysBackgroundImage> {

   Result<?> getBackgroundImage();

   List<SysBackgroundImage> getBackgroundImageList();

}
