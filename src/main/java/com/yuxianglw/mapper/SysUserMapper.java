package com.yuxianglw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxianglw.entity.SysUser;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("SELECT * FROM SYS_USER S1 WHERE S1.DEL_FLAG = '0' AND  S1.USER_NAME = #{username}")
    SysUser selectUserByName(String username);
}
