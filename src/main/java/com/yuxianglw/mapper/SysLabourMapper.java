package com.yuxianglw.mapper;

import com.yuxianglw.entity.SysLabour;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工  Mapper 接口
 * </p>
 *
 * @author zhangtao
 * @since 2020-11-12
 */
public interface SysLabourMapper extends BaseMapper<SysLabour> {
    /**
     * 根据身份证查询人员
     * @param iscard
     * @return
     */
   List<SysLabour> queryLabourByIdcard(@Param("idcard") String iscard);
   /**
     * 根据身份证查询人员
     * @param iscard
     * @return
     */
   List<SysLabour> accordingToIDQuery(@Param("idcard") String iscard,@Param("id") String id);

}
