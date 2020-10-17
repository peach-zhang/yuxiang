package com.yuxianglw.aop;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
 
@Aspect
@Component
public class LogAspect {
 
    /**
     * 定义切入点，切入点为cn.unbug.controller.*下所有的类的中的所有函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.yuxianglw.controller.*.*(..)))")
    public void BrokerPointcut(){
 
    }
 
    /**
    * @description  在连接点执行之前执行的通知
    */
    @Before("BrokerPointcut()")
    public void doBefore(){
        System.out.println("在连接点执行之前执行的通知！");
    }
 
    /**
     * @description  在连接点执行之后执行的通知（返回通知和异常通知的异常）
     */
    @After("BrokerPointcut()")
    public void doAfter(){
        System.out.println("在连接点执行之后执行的通知");
    }
 
    /**
     * @description  在连接点执行之后执行的通知（返回通知）
     */
    @AfterReturning("BrokerPointcut()")
    public void doAfterReturning(){
        System.out.println("在连接点执行之后执行的通知（返回通知）");
    }
 
    /**
     * @description  在连接点执行之后执行的通知（异常通知）
     */
    @AfterThrowing("BrokerPointcut()")
    public void doAfterThrowing(){
        System.out.println("在连接点执行之后执行的通知（异常通知）");
    }
}