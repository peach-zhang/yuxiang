package com.yuxianglw.config.jwt;

import com.yuxianglw.utlis.JWTUtils;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author zhangtao
 */
public class JWTToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return JWTUtils.getUsername(token);
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
