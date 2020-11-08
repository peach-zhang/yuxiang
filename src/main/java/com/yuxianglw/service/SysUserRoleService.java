package com.yuxianglw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysUserRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色关系 服务类
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    Result<?> addRoleForUser(Map<String,Object> param);
}
