package com.yuxianglw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysRole;

/**
 * <p>
 * 系统角色 服务类
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
public interface SysRoleService extends IService<SysRole> {
    /*
    * 获取角色列表
    */
    Result<?> queryRoles();
    /*
     * 获取用户未拥有的角色
     */
    Result<?> queryRolesThatDoNotExistForUsers(String userId);
    /*
     * 获取用户拥有的角色
     */
    Result<?> queryTheRolesTheUserHas(String userId);
}
