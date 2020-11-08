package com.yuxianglw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxianglw.common.BizConstant;
import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysUserRole;
import com.yuxianglw.mapper.SysUserRoleMapper;
import com.yuxianglw.service.SysUserRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色关系 服务实现类
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional
    public Result<?> addRoleForUser(Map<String,Object> param) {
        String userId = (String)param.get("userId");
        List<String> roles = (List<String>)param.get("roles");
        if(StringUtils.isBlank(userId)){return  Result.error(BizConstant.PARAMETER_IS_EMPTY);}
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("USER_ID",userId);
        sysUserRoleMapper.delete(wrapper);
        if(CollectionUtils.isNotEmpty(roles)){
            for (String role : roles) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(role);
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
        return Result.ok(BizConstant.SUCCESSFUL_OPERATION);
    }
}
