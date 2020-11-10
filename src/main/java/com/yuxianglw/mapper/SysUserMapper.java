package com.yuxianglw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxianglw.entity.SysUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser selectUserByName(String username);
}
