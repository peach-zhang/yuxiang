package com.yuxianglw.config.shiro;

import com.yuxianglw.config.jwt.JWTFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangtao
 */
@Configuration
public class ShiroConfig {

	@Bean
	public UserRealm userRealm(RedisCacheManager redisCacheManager){
		UserRealm userRealm = new UserRealm();
		//设置cachemanager
		userRealm.setCacheManager(redisCacheManager);
		//开启权限认证缓存
		userRealm.setCachingEnabled(true);
		userRealm.setAuthenticationCachingEnabled(true);
		userRealm.setAuthenticationCacheName("AuthenticationCache");
		userRealm.setAuthorizationCachingEnabled(true);
		userRealm.setAuthorizationCacheName("AuthorizationCache");
		return userRealm;
	}

	@Bean("securityManager")
	public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 使用自定义realm
		securityManager.setRealm(realm);
		//关闭session
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		securityManager.setSubjectDAO(subjectDAO);
		ThreadContext.bind(securityManager);
		return securityManager;
	}

	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

		// 添加自己的过滤器并且取名为jwt
		Map<String, Filter> filterMap = new HashMap<>(1);
		filterMap.put("jwt", new JWTFilter());
		factoryBean.setFilters(filterMap);
		factoryBean.setSecurityManager(securityManager);
		/*
		 * 自定义url规则
		 * http://shiro.apache.org/web.html#urls-
		 */
		Map<String, String> filterRuleMap = new HashMap<>(10);
		// 所有请求通过我们自己的JWT Filter
		filterRuleMap.put("/**", "jwt");
		// 访问401和404页面不通过我们的Filter
		filterRuleMap.put("/yuxianglw/login", "anon");
		//开放API文档接口
//		filterRuleMap.put("/swagger-ui.html", "anon");
//		filterRuleMap.put("/webjars/**","anon");
//		filterRuleMap.put("/swagger-resources/**","anon");
//		filterRuleMap.put("/v2/**","anon");
		//sql监控
		filterRuleMap.put("/druid/**","anon");
		factoryBean.setFilterChainDefinitionMap(filterRuleMap);
		return factoryBean;
	}

	/**
	 * 下面的代码是添加注解支持
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		// 强制使用cglib，防止重复代理和可能引起代理出错的问题
		// https://zhuanlan.zhihu.com/p/29161098
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	@Bean
	public RedisManager redisManager(RedisProperties redisProperties){
		RedisManager redisManager = new RedisManager();
		redisManager.setDatabase(redisProperties.getDatabase());
		redisManager.setHost(redisProperties.getHost()+":"+redisProperties.getPort());
		return redisManager;
	}

	@Bean
	public RedisCacheManager redisCacheManager(RedisManager redisManager){
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager);
		return redisCacheManager;
	}

}
