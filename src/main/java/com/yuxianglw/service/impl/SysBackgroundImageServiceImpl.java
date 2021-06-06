package com.yuxianglw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxianglw.entity.SysBackgroundImage;
import com.yuxianglw.mapper.SysBackgroundImageMapper;
import com.yuxianglw.service.SysBackgroundImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 登录背景图片  服务实现类
 * </p>
 *
 * @author zhangtao
 * @since 2020-11-01
 */
@Service
public class SysBackgroundImageServiceImpl extends ServiceImpl<SysBackgroundImageMapper, SysBackgroundImage> implements SysBackgroundImageService {

    @Autowired
    private SysBackgroundImageMapper sysBackgroundImageMapper;

    @Cacheable("BackgroundImage")
    @Override
    public List<SysBackgroundImage> getBackgroundImageList() {
        return sysBackgroundImageMapper.selectList(null);
    }

}
