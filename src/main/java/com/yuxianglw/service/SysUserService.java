package com.yuxianglw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysUser;
import org.apache.ibatis.annotations.Param;

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
     * @param id
     * @return
     */
    public Result<?> deleteUserById(@Param("id") String id);

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
    public Result<?> queryUser(String username,String phone,int pagenum, int pagesize);


}
