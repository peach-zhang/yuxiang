package com.yuxianglw.service;

import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysLabour;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 员工  服务类
 * </p>
 *
 * @author zhangtao
 * @since 2020-11-12
 */
public interface SysLabourService extends IService<SysLabour> {
    /**
     * 添加人员
     * @param sysLabour
     * @return
     */
    Result<?> addSysLabour(SysLabour sysLabour);

    /**
     * 根据条件查询
     * @param name
     * @param phone
     * @param idcard
     * @param pagenum
     * @param pagesize
     * @return
     */
    Result<?> queryUser(String name, String phone, String idcard,String sex,int pagenum, int pagesize);

    /**
     * 删除单个用户
     * @param sysLabour
     * @return
     */
    Result<?> deleteUser(SysLabour sysLabour);

    /**
     * 批量删除用户
     * @param sysLabours
     * @return
     */
    Result<?> batchDeleteUser(List<SysLabour> sysLabours);

    /**
     * 编辑用户
     * @param sysLabour
     * @return
     */
    Result<?> editUser(SysLabour sysLabour);

    /**
     * 获取上传模板
     * @param response
     * @return
     */
    void getTheTemplate(HttpServletResponse response);

    /**
     * 导出人员列表
     * @param response
     * @param name
     * @param phone
     * @param idcard
     * @param sex
     */
    void userListDownload(HttpServletResponse response, String name, String phone, String idcard, String sex);

    /**
     * 批量上传名单
     * @param file
     * @return
     */
    Result<?> batchUploadSysLabour(MultipartFile file);
}
