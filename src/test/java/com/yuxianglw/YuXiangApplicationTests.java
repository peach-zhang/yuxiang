package com.yuxianglw;

import com.yuxianglw.config.redis.RedisUtils;
import com.yuxianglw.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YuXiangApplicationTests {

    @Autowired
    RedisProperties RedisProperties;

    @Test
    void contextLoads() {
        System.out.println(RedisProperties.getHost());
    }

}
