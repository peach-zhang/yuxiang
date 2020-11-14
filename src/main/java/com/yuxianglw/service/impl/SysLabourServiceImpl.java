package com.yuxianglw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxianglw.common.BizConstant;
import com.yuxianglw.common.CommonConstant;
import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysLabour;
import com.yuxianglw.entity.SysUser;
import com.yuxianglw.mapper.SysLabourMapper;
import com.yuxianglw.mapper.SysUserMapper;
import com.yuxianglw.service.SysLabourService;
import com.yuxianglw.utlis.CardUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 员工  服务实现类
 * </p>
 *
 * @author zhangtao
 * @since 2020-11-12
 */
@Service
public class SysLabourServiceImpl extends ServiceImpl<SysLabourMapper, SysLabour> implements SysLabourService {

    @Autowired
    private SysLabourMapper sysLabourMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Result<?> addSysLabour(SysLabour sysLabour) {
        String sysUserName = (String) SecurityUtils.getSubject().getPrincipal();
        //获取身份证号码
        final String idcard = sysLabour.getIdcard();
        final List<SysLabour> sysLabours = sysLabourMapper.queryLabourByIdcard(idcard);
        if(CollectionUtils.isNotEmpty(sysLabours)){
            return Result.error("身份证已经存在！");
        }
        final Integer carInfo = CardUtil.getCarInfo(idcard);
        sysLabour.setAge(carInfo);
        final SysUser sysUser = sysUserMapper.selectUserByName(sysUserName);
        Assert.notNull(sysUser,"无法获取当前用户！");
        sysLabour.setBelong(sysUser.getId());
        sysLabourMapper.insert(sysLabour);
        return Result.ok(BizConstant.SAVED_SUCCESSFULLY);
    }

    @Override
    public Result<?> queryUser(String name, String phone, String idcard, String sex,int pagenum, int pagesize) {
        String sysUserName = (String) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<SysLabour> wapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)) {wapper.eq("NAME",name);}
        if(StringUtils.isNotBlank(phone)) {wapper.eq("PHONE",phone);}
        if(StringUtils.isNotBlank(idcard)) {wapper.eq("IDCARD",idcard);}
        if(StringUtils.isNotBlank(sex)) {wapper.eq("SEX",sex);}
        wapper.eq("DEL_FLAG", CommonConstant.DEL_FLAG_0);
        //只能查看自己的用户
        final SysUser belongUser = sysUserMapper.selectUserByName(sysUserName);
        wapper.eq("BELONG",belongUser.getId());
        Assert.notNull(belongUser,"无法获取当前用户！");
        Page<SysLabour> page = new Page<>();
        page.setSize(pagesize).setCurrent(pagenum);
        final Page<SysLabour> sysLabourPage = sysLabourMapper.selectPage(page, wapper);
        final List<SysLabour> sysLabours = sysLabourPage.getRecords();
        for (SysLabour sysLabour : sysLabours) {
            final String belong = sysLabour.getBelong();
            final String createdName = sysLabour.getCreatedBy();
            //所属人
            final SysUser sysUser = sysUserMapper.selectById(belong);
            Assert.notNull(sysUser,"查询不到所属人！");
            sysLabour.setBelong(sysUser.getRealName());
            //创建人
            final SysUser creatUser = sysUserMapper.selectUserByName(createdName);
            Assert.notNull(creatUser,"查询不到创建人！");
            sysLabour.setCreatedBy(creatUser.getRealName());
        }
        return Result.ok(sysLabourPage);
    }

    @Override
    @Transactional
    public Result<?> deleteUser(SysLabour sysLabour) {
        sysLabourMapper.deleteById(sysLabour.getId());
        return Result.ok(BizConstant.DETELED_SUCCESSFULLY);
    }

    @Override
    @Transactional
    public Result<?> batchDeleteUser(List<SysLabour> sysLabours) {
        final List<String> ids = sysLabours.stream().map(SysLabour::getId).collect(Collectors.toList());
        sysLabourMapper.deleteBatchIds(ids);
        return Result.ok(BizConstant.DETELED_SUCCESSFULLY);
    }

    @Override
    @Transactional
    public Result<?> editUser(SysLabour sysLabour) {
        SysLabour labour = sysLabourMapper.selectById(sysLabour.getId());
        Assert.notNull(labour,"无可编辑用户!");
        final String idcard = sysLabour.getIdcard();
        final List<SysLabour> sysLabours = sysLabourMapper.accordingToIDQuery(idcard,sysLabour.getId());
        if(CollectionUtils.isNotEmpty(sysLabours)){
            return Result.error("身份证已经存在！");
        }
        final Integer age = CardUtil.getCarInfo(sysLabour.getIdcard());
        labour.setIdcard(idcard);
        labour.setAge(age);
        labour.setName(sysLabour.getName());
        labour.setPhone(sysLabour.getPhone());
        labour.setSex(sysLabour.getSex());
        sysLabourMapper.updateById(labour);
        return Result.ok(BizConstant.SUCCESSFUL_OPERATION);
    }
}
