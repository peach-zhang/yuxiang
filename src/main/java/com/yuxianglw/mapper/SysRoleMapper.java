package com.yuxianglw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxianglw.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统角色 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> queryRoleByUserId(@Param("userId") String userId);

    List<SysRole> queryRolesThatDoNotExistForUsers(@Param("userId") String userId);
}
