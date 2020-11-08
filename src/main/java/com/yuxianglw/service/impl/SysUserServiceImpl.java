package com.yuxianglw.service.impl;

import cn.hutool.core.util.RandomUtil;
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
import com.yuxianglw.utlis.StrUtils;
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
import java.util.stream.Collectors;

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
    public Result<?> deleteUserById(SysUserDTO sysUserDTO) {
        SysUser sysUser = sysUserMapper.selectById(sysUserDTO.getId());
        if(Objects.nonNull(sysUser)){
            if(StringUtils.equals(BizConstant.SUPER_ADMINISTRATOR,sysUser.getUserName())){
                return Result.error("超级管理员不可删除!");
            }
            QueryWrapper<SysUser> wapper = new QueryWrapper<>();
            wapper.eq("SUPERIOR_ID",sysUserDTO.getId());
            final List<SysUser> sysUsers = sysUserMapper.selectList(wapper);
            if(CollectionUtils.isNotEmpty(sysUsers)){return Result.error("用户下存在代理不可删除！");}
            redisUtils.clearUserCache(sysUser);
            sysUserMapper.deleteById(sysUser.getId());
            return Result.ok("删除成功!");
        }
        return Result.error("删除对象不存在!");
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
            redisUtils.set(sysUser.getUserName() + BizConstant.CACHE_TOKEN,token,BizConstant.EXPIRE_TIME);
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
        wapper.eq("DEL_FLAG",CommonConstant.DEL_FLAG_0);
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
                //创建人
                SysUser CreatedBy = this.queryUserByName(sysUser.getCreatedBy());
                if(Objects.nonNull(CreatedBy)){sysUserDTO.setCreatedBy(CreatedBy.getRealName());}
                //更新人
                SysUser UpdatedBy = this.queryUserByName(sysUser.getUpdatedBy());
                if(Objects.nonNull(UpdatedBy)){sysUserDTO.setUpdatedBy(UpdatedBy.getRealName());}
                //状态转换
                boolean status = this.statusAdapter(sysUser.getStatus());
                sysUserDTO.setStatus(status);
                //性别中英转换
                sysUserDTO.setSex(StrUtils.sxeTransformChinese(sysUserDTO.getSex()));
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
        String booleanStatus = status?CommonEnum.ACCOUNT_NUMBER_ACTIVE.getCode():CommonEnum.ACCOUNT_NUMBER_LOCK.getCode();
        sysUser.setStatus(booleanStatus);
        sysUserMapper.updateById(sysUser);
        redisUtils.clearUserCache(sysUser);
        return Result.ok(BizConstant.SUCCESSFUL_OPERATION);
    }

    @Override
    public Result<?> addUser(SysUser sysUser) {
        if(StringUtils.isBlank(sysUser.getUserName()) || StringUtils.isBlank(sysUser.getRealName())
                ||StringUtils.isBlank(sysUser.getEmail()) || StringUtils.isBlank(sysUser.getPhone())){
            return Result.error("用户参数不可为空");
        }
        //判断用户名是否存在
        QueryWrapper<SysUser> wapper = new QueryWrapper<>();
        wapper.eq("USER_NAME",sysUser.getUserName());
        final List<SysUser> sysUsers = sysUserMapper.selectList(wapper);
        if(CollectionUtils.isNotEmpty(sysUsers)){return Result.error("该用户名已存在！");}
        //盐
        final String salt = RandomUtil.randomString(10);
        sysUser.setSalt(salt);
        //设置默认密码
        final String password = MD5Utils.md5Encryption(BizConstant.DEFAULT_PASSWORD, salt);
        sysUser.setPassWord(password);
        //保存
        sysUserMapper.insert(sysUser);
        return Result.ok("添加成功");
    }

    @Override
    @Transactional
    public Result<?> batchDeleteUser(List<SysUserDTO> sysUserDTOS) {
        String oneself =(String)SecurityUtils.getSubject().getPrincipal();
        final List<SysUserDTO> Root = sysUserDTOS.stream().filter(user -> StringUtils.equals(user.getUserName(), BizConstant.SUPER_ADMINISTRATOR)
                ||StringUtils.equals(user.getUserName(), oneself)).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(Root)){
            return Result.error("不可删除管理员/自己！");
        }
        final List<String> ids = sysUserDTOS.stream().map(user -> user.getId()).collect(Collectors.toList());
        QueryWrapper<SysUser> wapper = new QueryWrapper<>();
        for (String id : ids) {
            wapper.eq("SUPERIOR_ID",id);
            final List<SysUser> sysUsers = sysUserMapper.selectList(wapper);
            if(CollectionUtils.isNotEmpty(sysUsers)){
                return Result.error("用户下存在代理不可删除！");
            }
        }
        sysUserMapper.deleteBatchIds(ids);
        return Result.ok("批量删除成功");
    }

    @Override
    @Transactional
    public Result editUser(SysUserDTO sysUserDTO) {
        final SysUser sysUser = sysUserMapper.selectById(sysUserDTO.getId());
        if(Objects.isNull(sysUser)){
            return Result.error("编辑对象不存在！");
        }
        sysUser.setSex(sysUserDTO.getSex());
        sysUser.setPhone(sysUserDTO.getPhone());
        sysUser.setEmail(sysUserDTO.getEmail());
        sysUser.setSuperiorId(sysUserDTO.getSuperiorId());
        sysUser.setRealName(sysUserDTO.getRealName());
        sysUserMapper.updateById(sysUser);
        return Result.ok("编辑成功！");
    }

    @Override
    public Result<?> queryUserInfo() {
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        final SysUser sysUser = sysUserMapper.selectUserByName(username);
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(sysUser,sysUserDTO);
        //性别中英转换
        sysUserDTO.setSex(StrUtils.sxeTransformChinese(sysUserDTO.getSex()));
        return Result.ok(sysUserDTO);
    }

    /*根据名称获取用户*/
    private SysUser queryUserByName(String userName){
        if(StringUtils.isNotBlank(userName)){
            QueryWrapper<SysUser> wapper = new QueryWrapper<>();
            wapper.eq("USER_NAME",userName).eq("DEL_FLAG",CommonConstant.DEL_FLAG_0);
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
}
