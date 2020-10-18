package com.yuxianglw.mapper;

import com.yuxianglw.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 系统角色 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> queryRoleByUserId(String id);
}
