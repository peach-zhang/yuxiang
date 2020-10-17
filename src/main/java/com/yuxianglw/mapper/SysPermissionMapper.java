package com.yuxianglw.mapper;

import com.yuxianglw.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-17
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> queryPermissionByRoleIds(@Param("roleIds") List<String> roleIds);
}