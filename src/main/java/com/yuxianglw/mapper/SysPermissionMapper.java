package com.yuxianglw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxianglw.entity.SysPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 权限表  Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-25
 */
@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     * 根据角色查询角色对应的权限
     * @param roleIds
     * @return
     */
    List<SysPermission> queryPermissionByRoleIds(List<String> roleIds);
}
