package com.yuxianglw.service.impl;

import com.yuxianglw.common.CommonConstant;
import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysUser;
import com.yuxianglw.mapper.SysUserMapper;
import com.yuxianglw.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

}
