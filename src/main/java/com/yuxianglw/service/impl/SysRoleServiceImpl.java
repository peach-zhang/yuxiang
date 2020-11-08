package com.yuxianglw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysRole;
import com.yuxianglw.mapper.SysRoleMapper;
import com.yuxianglw.service.SysRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色 服务实现类
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Result<?> queryRoles() {
        final List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        return Result.ok(sysRoles);
    }

    @Override
    public Result<?> queryRolesThatDoNotExistForUsers(String userId) {
        final List<SysRole> sysRoles = sysRoleMapper.queryRolesThatDoNotExistForUsers(userId);
        return Result.ok(sysRoles);
    }

    @Override
    public Result<?> queryTheRolesTheUserHas(String userId) {
        List<SysRole> sysRoles = sysRoleMapper.queryRoleByUserId(userId);
        if(CollectionUtils.isNotEmpty(sysRoles)){
            final List<String> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
            return Result.ok(roleIds);
        }
        return Result.ok();
    }
}
