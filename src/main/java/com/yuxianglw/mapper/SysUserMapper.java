package com.yuxianglw.mapper;

import com.yuxianglw.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("SELECT * FROM SYS_USER S1 WHERE  S1.USER_NAME = #{username}")
    List<SysUser> selectUserByName(String username);
}
