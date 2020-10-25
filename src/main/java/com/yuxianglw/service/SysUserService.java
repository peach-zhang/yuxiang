package com.yuxianglw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysUser;
import org.apache.ibatis.annotations.Param;

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
    String login(String username, String password);

}
