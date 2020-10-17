package com.yuxianglw.mapper;

import com.yuxianglw.entity.SysPermission;
import com.yuxianglw.entity.SysRolePermission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysPermissionMapperTest {
    
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    
    @Test
    void test(){
        String [] ids ={"1"};
        List<SysPermission> sysPermissions = sysPermissionMapper.queryPermissionByRoleIds(Arrays.asList(ids));
        for (SysPermission sysPermission : sysPermissions) {
            System.out.println(sysPermission);
        }
    }

}