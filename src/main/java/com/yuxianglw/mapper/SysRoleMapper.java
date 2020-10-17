package com.yuxianglw.mapper;

import com.yuxianglw.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统角色 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-17
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> queryRoleByUserId(@Param("userId") String userId);
}
