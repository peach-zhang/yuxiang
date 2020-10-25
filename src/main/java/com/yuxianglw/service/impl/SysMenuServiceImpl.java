package com.yuxianglw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysMenu;
import com.yuxianglw.entity.dto.SysMenuVo;
import com.yuxianglw.mapper.SysMenuMapper;
import com.yuxianglw.service.SysMenuService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单  服务实现类
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-25
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private  SysMenuMapper sysMenuMapper;

    @Override
    public Result<?> queryMenuByUser() {
        List<SysMenuVo> sysMenuVos = new ArrayList<>();
        List<SysMenu> sysMenus = sysMenuMapper.queryParentMenu();
        if(CollectionUtils.isNotEmpty(sysMenus)){
            for (SysMenu sysMenu : sysMenus) {
                SysMenuVo sysMenuVo = new SysMenuVo();
                BeanUtils.copyProperties(sysMenu,sysMenuVo);
                List<SysMenu> menus = sysMenuMapper.queryMenuByParent(sysMenu.getId());
                sysMenuVo.setChilds(menus);
                sysMenuVos.add(sysMenuVo);
            }
        }
        return Result.ok(sysMenuVos);
    }
}
