package com.yuxianglw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysUser;
import com.yuxianglw.entity.dto.SysUserDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
public interface SysUserService extends IService<SysUser> {

    /**
     *
     * @param sysUserDTO
     * @return
     */
    public Result<?> deleteUserById(SysUserDTO sysUserDTO);

    /**
     * 用户登入
     *
     * @param username
     * @param password
     * @return
     */
    Map<String,String> login(String username, String password);

    /**
     * 退出
     * @return
     */
    Result<?> logout();

    /*
     *获取用户列表
     *
     */
    Result<?> queryUser(String username,String phone,int pagenum, int pagesize);

    /*
     *修改用户的状态
     *
     */
    Result<?> editUserStatus(SysUserDTO sysUserDTO);
    /*
     *添加系统用户
     *
     */
    Result<?> addUser(SysUser sysUser);
    /*
     *批量删除用户
     *
     */
    Result<?> batchDeleteUser(List<SysUserDTO> sysUserDTOS);
    /*
     *修改用户
     *
     */
    Result editUser(SysUserDTO sysUserDTO);
    /*
     *获取登录用户信息
     *
     */
    Result<?> queryUserInfo();

    /**
     * 修改密码
     * @param passWord
     * @return
     */
    Result<?> changePassWord(Map<String, String> passWord);
}
