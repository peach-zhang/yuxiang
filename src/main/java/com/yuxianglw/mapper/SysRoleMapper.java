package com.yuxianglw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxianglw.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 系统角色 Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-10-18
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户id 查询用户角色
     * @param userId
     * @return
     */
    List<SysRole> queryRoleByUserId(@Param("userId") String userId);

    /**
     * 查询用户不存在的角色
     * @param userId
     * @return
     */
    List<SysRole> queryRolesThatDoNotExistForUsers(@Param("userId") String userId);
}
