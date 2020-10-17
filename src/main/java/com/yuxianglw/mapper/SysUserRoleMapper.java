package com.yuxianglw.mapper;

import com.yuxianglw.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户角色关系 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-17
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {


    List<SysUserRole> queryRoleByUserId(@Param("userId") String userId);
}
