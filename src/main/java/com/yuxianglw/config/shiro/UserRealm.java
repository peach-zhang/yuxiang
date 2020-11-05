package com.yuxianglw.config.shiro;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxianglw.common.*;
import com.yuxianglw.config.jwt.JWTToken;
import com.yuxianglw.config.redis.RedisUtils;
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
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangtao
 *
 */
@Component
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private RedisUtils redisUtils;

	public UserRealm() {
		//开启权限认证缓存
		super.setCachingEnabled(true);
		super.setAuthenticationCachingEnabled(true);
		super.setAuthenticationCacheName(CommonConstant.AUTHENTICATIONCACHE);
		super.setAuthorizationCachingEnabled(true);
		super.setAuthorizationCacheName(CommonConstant.AUTHORIZATIONCACHE);
	}
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

		if (StringUtils.isBlank(username)) {
			throw new AuthenticationException(ErrorCodeEnum.USER_PWD_ACCOUNT_NOT_FOUND.getResultMsg());
		}
		SysUser sysUser = sysUserMapper.selectUserByName(username);

		if (Objects.isNull(sysUser)) {
			throw new AccountException(ErrorCodeEnum.USER_PWD_ACCOUNT_NOT_FOUND.getResultMsg());
		}
		if(JWTUtils.isExpire(token)){
			redisUtils.del(username + BizConstant.CACHE_USER);
			redisUtils.del(username + BizConstant.CACHE_TOKEN);
			throw new AuthenticationException(ErrorCodeEnum.EXPIRED_TOKEN.getResultMsg());
		}

		if (!JWTUtils.verify(token, username, sysUser.getPassWord())) {
			redisUtils.del(username + BizConstant.CACHE_USER);
			redisUtils.del(username + BizConstant.CACHE_TOKEN);
			throw new CredentialsException(ErrorCodeEnum.USER_PWD_ACCOUNT_NOT_FOUND.getResultMsg());
		}

		if(StringUtils.equals(sysUser.getStatus(), CommonEnum.ACCOUNT_NUMBER_LOCK.getCode())){
			throw new LockedAccountException(CommonEnum.ACCOUNT_NUMBER_LOCK.getMsg());
		}
		//保存用户信息
		redisUtils.set(username + BizConstant.CACHE_USER,sysUser,BizConstant.EXPIRE_TIME);
		return new SimpleAuthenticationInfo(username, token, getName());
	}

	/**
	 * 清除当前用户的权限认证缓存
	 * @param principals 权限信息
	 */
	@Override
	public void clearCache(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		redisUtils.del(username + BizConstant.CACHE_USER);
		redisUtils.del(username + BizConstant.CACHE_TOKEN);
		super.clearCache(principals);
	}

}
