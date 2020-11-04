package com.yuxianglw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxianglw.common.*;
import com.yuxianglw.config.jwt.JWTToken;
import com.yuxianglw.config.redis.RedisUtils;
import com.yuxianglw.entity.SysUser;
import com.yuxianglw.entity.dto.SysUserDTO;
import com.yuxianglw.mapper.SysUserMapper;
import com.yuxianglw.service.SysUserService;
import com.yuxianglw.utlis.JWTUtils;
import com.yuxianglw.utlis.MD5Utils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

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
    private RedisUtils redisUtils;

    @Override
    @Transactional
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
    @Transactional
    public Map<String,String> login(String username, String password) {
        String token;
        SysUser sysUser = sysUserMapper.selectUserByName(username);
        Map<String,String> data = new HashMap<>();
        if (sysUser != null) {
            data.put("realName",sysUser.getRealName());
            token = (String)redisUtils.get(sysUser.getUserName() + BizConstant.CACHE_TOKEN);
            if(StringUtils.isNotBlank(token)){
                JWTToken jwtToken = new JWTToken(token);
                SecurityUtils.getSubject().login(jwtToken);
                data.put("token",token);
                return data;
            }
            String salt = sysUser.getSalt();
            //秘钥为盐
            String target = MD5Utils.md5Encryption(password, salt);
            //生成Token
            token = JWTUtils.sign(username, target);
            data.put("token",token);
            JWTToken jwtToken = new JWTToken(token);
            SecurityUtils.getSubject().login(jwtToken);
            redisUtils.set(sysUser.getUserName() + BizConstant.CACHE_TOKEN,token);
        } else {
            throw new ServiceException(ErrorCodeEnum.USER_PWD_ACCOUNT_NOT_FOUND);
        }
        return data;
    }

    @Override
    public Result<?> logout() {
        try {
            SecurityUtils.getSubject().logout();
        }catch (Exception e){
            log.error("退出失败{}",e);
            throw new ServiceException(ErrorCodeEnum.LOGIN_OUT_FAIL);
        }
        return Result.ok();
    }

    @Override
    public Result<?> queryUser(String username, String phone, int pagenum, int pagesize) {
        QueryWrapper<SysUser> wapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)) {wapper.eq("USER_NAME",username);}
        if(StringUtils.isNotBlank(phone)) {wapper.eq("PHONE",phone);}
        Integer integer = sysUserMapper.selectCount(wapper);
        Page<SysUser> sysUserPage = new Page<>();
        Page<SysUserDTO> sysUserDTOPage = new Page<>();
        sysUserPage.setSize(pagesize).setCurrent(pagenum);
        List<SysUserDTO> sysUserDTOList= new ArrayList<>();
        if(Objects.nonNull(integer) && integer>0){
            sysUserPage.setTotal(integer);
            sysUserPage= sysUserMapper.selectPage(sysUserPage, wapper);
            //capy数据
            BeanUtils.copyProperties(sysUserPage,sysUserDTOPage);
            List<SysUser> records = sysUserPage.getRecords();
            for (SysUser sysUser : records) {
                SysUserDTO sysUserDTO = new SysUserDTO();
                BeanUtils.copyProperties(sysUser,sysUserDTO);
                SysUser CreatedBy = this.queryUserByName(sysUser.getCreatedBy());
                if(Objects.nonNull(CreatedBy)){sysUserDTO.setCreatedBy(CreatedBy.getRealName());}
                SysUser UpdatedBy = this.queryUserByName(sysUser.getUpdatedBy());
                if(Objects.nonNull(UpdatedBy)){sysUserDTO.setUpdatedBy(UpdatedBy.getRealName());}
                boolean status = this.statusAdapter(sysUser.getStatus());
                sysUserDTO.setStatus(status);
                SysUser superiorUser = this.queryUserByid(sysUser.getSuperiorId());
                if(Objects.nonNull(superiorUser)){sysUserDTO.setSuperiorName(superiorUser.getRealName());}
                sysUserDTOList.add(sysUserDTO);
            }
            sysUserDTOPage.setRecords(sysUserDTOList);
            return Result.ok(sysUserDTOPage);
        }
        return Result.ok(sysUserDTOPage);
    }

    @Override
    @Transactional
    public Result<?> editUserStatus(SysUserDTO sysUserDTO) {
        if(Objects.isNull(sysUserDTO)){return Result.error(BizConstant.PARAMETER_IS_EMPTY);};
        String id = sysUserDTO.getId();
        boolean status =sysUserDTO.getStatus();
        if(StringUtils.isBlank(id) || Objects.isNull(status)){
            return Result.error(BizConstant.PARAMETER_IS_EMPTY);
        }
        SysUser sysUser = sysUserMapper.selectById(id);
        //判断用户是否为空
        if(Objects.isNull(sysUser)){
            return Result.error(BizConstant.NO_CORRESPONDING_USER);
        }
        //判断是否是自己
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.equals(username,sysUser.getUserName())){
            return Result.error(BizConstant.DONT_BAN_YOURSELF);
        }
        //超级管理员不可以禁止
        if(StringUtils.equals(sysUser.getUserName(),BizConstant.SUPER_ADMINISTRATOR)){
            return Result.error(BizConstant.NOT_FORBIDDEN);
        }
        //状态转换和设置状态
        String _status = status?CommonEnum.ACCOUNT_NUMBER_ACTIVE.getCode():CommonEnum.ACCOUNT_NUMBER_LOCK.getCode();
        sysUser.setStatus(_status);
        sysUserMapper.updateById(sysUser);
        this.clearUserCache(sysUser);
        return Result.ok(BizConstant.SUCCESSFUL_OPERATION);
    }

    /*根据名称获取用户*/
    private SysUser queryUserByName(String userName){
        if(StringUtils.isNotBlank(userName)){
            QueryWrapper<SysUser> wapper = new QueryWrapper<>();
            wapper.eq("USER_NAME",userName);
            List<SysUser> sysUsers = sysUserMapper.selectList(wapper);
            if(CollectionUtils.isNotEmpty(sysUsers)){
                return sysUsers.get(0);
            }
        }
        return  null;
    }
    /*状态中英文转换*/
    private boolean statusAdapter(String status){
        if(StringUtils.equals(status, CommonEnum.ACCOUNT_NUMBER_ACTIVE.getCode())){
            return true;
        }else if(StringUtils.equals(status, CommonEnum.ACCOUNT_NUMBER_LOCK.getCode())){
            return false;
        }
        return false;
    }

    /*根据id 查询除上级*/
    private SysUser queryUserByid(String id){
        return  sysUserMapper.selectById(id);
    }

    /*清除用户的缓存*/
    @Override
    public void clearUserCache(SysUser sysUser){
        //清除用户缓存
        redisUtils.del(sysUser.getUserName()+BizConstant.CACHE_USER);
        //清除token缓存
        redisUtils.del(sysUser.getUserName()+BizConstant.CACHE_TOKEN);
        //清除认证信息
        redisUtils.del(BizConstant.AUTHENTICATION_CACHE+sysUser.getUserName());
        //清除授权信息
        redisUtils.del(BizConstant.AUTHORIZATION_CACHE+sysUser.getUserName());
    }
}
