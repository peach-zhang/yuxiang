package com.yuxianglw.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxianglw.common.CommonConstant;
import com.yuxianglw.common.ErrorCodeEnum;
import com.yuxianglw.common.Result;
import com.yuxianglw.common.ServiceException;
import com.yuxianglw.config.jwt.JWTToken;
import com.yuxianglw.entity.SysUser;
import com.yuxianglw.mapper.SysUserMapper;
import com.yuxianglw.service.SysUserService;
import com.yuxianglw.utlis.JWTUtils;
import com.yuxianglw.utlis.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.PrincipalCollection;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private  SysUserMapper sysUserMapper;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    public Result<?> deleteUserById(String id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(Objects.nonNull(sysUser)){
            sysUser.setDelFlag(CommonConstant.DEL_FLAG_1);
            int i = sysUserMapper.updateById(sysUser);
            if(i>0){
                return Result.ok("删除成功");
            }else{
                return Result.error("删除失败");
            }
        }
        return Result.error("删除对象不存在");
    }

    @Override
    public String login(String username, String password) {
        String token;
        SysUser sysUser = sysUserMapper.selectUserByName(username);
        if (sysUser != null) {
            String salt = sysUser.getSalt();
            //秘钥为盐
            String target = MD5Utils.md5Encryption(password, salt);
            //生成Token
            token = JWTUtils.sign(username, target);
            JWTToken jwtToken = new JWTToken(token);
            try {
                SecurityUtils.getSubject().login(jwtToken);
            } catch (AuthenticationException e) {
                throw new ServiceException(e.getMessage());
            }
        } else {
            throw new ServiceException(ErrorCodeEnum.USER_ACCOUNT_NOT_FOUND);
        }
        return token;
    }

    @Override
    public Result<?> loginout() {
        try {
            PrincipalCollection previousPrincipals = SecurityUtils.getSubject().getPrincipals();
            Cache<Object, Object> cache = redisCacheManager.getCache(CommonConstant.AUTHENTICATIONCACHE);
            System.err.println(JSON.toJSONString(cache));
            redisCacheManager.getCache(CommonConstant.AUTHORIZATIONCACHE).remove(previousPrincipals);
            SecurityUtils.getSubject().logout();
        }catch (Exception e){
          throw new ServiceException(ErrorCodeEnum.LOGIN_OUT_FAIL);
        }
        return Result.ok();
    }

}
