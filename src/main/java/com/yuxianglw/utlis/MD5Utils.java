package com.yuxianglw.utlis;

import com.yuxianglw.common.BizConstant;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @Author zhangtao
 * @Date 2020/3/1 12:33
 * @Version 1.0
 **/
public class MD5Utils {

    /**
     * 密码加密
     * @return
     */
    public static String md5Encryption(String source,String salt){
        //加密算法
        String algorithmName = "MD5";
        //加密次数
        int hashIterations = 1024;
        SimpleHash simpleHash = new SimpleHash(algorithmName,source,salt,hashIterations);
        return simpleHash+"";
    }

}
