package com.yuxianglw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author zhangtao
 */
@SpringBootApplication
@MapperScan("com.yuxianglw.mapper")
@EnableCaching
public class YuXiangApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuXiangApplication.class, args);
    }

}
