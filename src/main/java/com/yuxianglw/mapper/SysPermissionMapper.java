package com.yuxianglw.mapper;

import com.yuxianglw.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    void queryPermissionByRoleIds(List<String> roleIds);
}
