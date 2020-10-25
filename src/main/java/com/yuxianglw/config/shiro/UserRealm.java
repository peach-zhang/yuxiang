package com.yuxianglw.config.shiro;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxianglw.common.CommonEnum;
import com.yuxianglw.config.jwt.JWTToken;
import com.yuxianglw.entity.SysPermission;
import com.yuxianglw.entity.SysRole;
import com.yuxianglw.entity.SysUser;
import com.yuxianglw.entity.SysUserRole;
import com.yuxianglw.mapper.SysPermissionMapper;
import com.yuxianglw.mapper.SysRoleMapper;
import com.yuxianglw.mapper.SysUserMapper;
import com.yuxianglw.utlis.JWTUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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

	/**
	 * 大坑！，必须重写此方法，不然Shiro会报错
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}

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
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {

		String token = (String) auth.getCredentials();
		// 解密获得username，用于和数据库进行对比
		String username = JWTUtils.getUsername(token);

		if (username == null) {
			throw new AuthenticationException(" token错误，请重新登入！");
		}
		SysUser sysUser = sysUserMapper.selectUserByName(username);

		if (sysUser == null) {
			throw new AccountException("账号不存在!");
		}
		if(JWTUtils.isExpire(token)){
			throw new AuthenticationException(" token过期，请重新登入！");
		}

		if (!JWTUtils.verify(token, username, sysUser.getPassWord())) {
			throw new CredentialsException("密码错误!");
		}

		if(StringUtils.equals(sysUser.getStatus(), CommonEnum.ACCOUNT_NUMBER_LOCK.getCode())){
			throw new LockedAccountException(CommonEnum.ACCOUNT_NUMBER_LOCK.getMsg());
		}

		return new SimpleAuthenticationInfo(sysUser, token, getName());
	}

	/**
	 * 重写方法,清除当前用户的的 授权缓存
	 * @param principals
	 */
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 重写方法，清除当前用户的 认证缓存
	 * @param principals
	 */
	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

}
