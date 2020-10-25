package com.yuxianglw.mapper;

import com.yuxianglw.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单  Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-25
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /*
    *根据Parent 查找子类菜单
    */
    public List<SysMenu> queryMenuByParent(@Param("parendId") String parendId);

    /*
     *获取父级菜单
     */
    public List<SysMenu> queryParentMenu();
}
