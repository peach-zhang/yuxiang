package com.yuxianglw.service;

import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单  服务类
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-25
 */
public interface SysMenuService extends IService<SysMenu> {
    /*
    * 获取当前用户对用的权限菜单
    */
    public Result<?> queryMenuByUser();

}
