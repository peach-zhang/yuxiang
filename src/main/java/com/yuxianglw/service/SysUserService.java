package com.yuxianglw.service;

import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

}
