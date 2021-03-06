package com.yuxianglw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxianglw.entity.SysMenu;
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

    /**
     * 根据Parent 查找子类菜单
     * @param parendId
     * @return
     */
     List<SysMenu> queryMenuByParent(@Param("parendId") String parendId);

    /**
     * 获取父级菜单
     * @return
     */
     List<SysMenu> queryParentMenu();
}
