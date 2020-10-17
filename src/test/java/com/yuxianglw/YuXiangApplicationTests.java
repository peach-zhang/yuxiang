package com.yuxianglw;

import com.yuxianglw.config.redis.RedisUtils;
import com.yuxianglw.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YuXiangApplicationTests {

    @Autowired
    RedisUtils RedisUtils;

    @Test
    void contextLoads() {
        RedisUtils.set("123","456");
        //RedisUtils.setRemove("123");
        SysUser sysUser = new SysUser();
        sysUser.setId("123456789");
        RedisUtils.set("user",sysUser);
    }

}
