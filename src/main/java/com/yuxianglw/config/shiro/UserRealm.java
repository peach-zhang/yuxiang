package com.yuxianglw.config.shiro;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxianglw.entity.SysPermission;
import com.yuxianglw.entity.SysRole;
import com.yuxianglw.entity.SysUser;
import com.yuxianglw.entity.SysUserRole;
import com.yuxianglw.mapper.SysPermissionMapper;
import com.yuxianglw.mapper.SysRoleMapper;
import com.yuxianglw.mapper.SysUserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangtao
 *
 */
public class UserRealm extends AuthorizingRealm {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	/* 授权 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		SysUser sysUser  = (SysUser) principals.getPrimaryPrincipal();
		new QueryWrapper<SysUserRole>();
		List<SysRole> sysRoles = sysRoleMapper.queryRoleByUserId(sysUser.getId());
		if(CollectionUtils.isNotEmpty(sysRoles)){
			List<String> roles = sysRoles.stream().map(SysRole::getRoleName).collect(Collectors.toList());
			List<String> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
			authorizationInfo.addRoles(roles);
			List<SysPermission> sysPermissions = sysPermissionMapper.queryPermissionByRoleIds(roleIds);
			if(CollectionUtils.isNotEmpty(sysPermissions)){
				Set<String> permissions = sysPermissions.stream().map(SysPermission::getName).collect(Collectors.toSet());
				authorizationInfo.addStringPermissions(permissions);
			}

		}
        return authorizationInfo;
	}

	/* 认证 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken UsernamePasswordToken = (UsernamePasswordToken) token;
		SysUser sysUser = sysUserMapper.selectUserByName(UsernamePasswordToken.getUsername()).get(0);
		if(Objects.nonNull(sysUser)) {
			return new SimpleAuthenticationInfo(sysUser, sysUser.getPassWord(),getName());
		}
		return null;
	}

}
