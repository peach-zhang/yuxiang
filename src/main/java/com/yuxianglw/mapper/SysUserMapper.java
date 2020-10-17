package com.yuxianglw.mapper;

import com.yuxianglw.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-17
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * fetch data by rule id
     *
     * @param username
     * @return Result<XxxxDO>
     */
    @Select("select * from sys_user user where user.user_name= #{username}")
    SysUser selectUserByName(@Param("username")  String username);
}
