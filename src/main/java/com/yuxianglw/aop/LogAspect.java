package com.yuxianglw.aop;

import com.alibaba.fastjson.JSON;
import com.yuxianglw.common.BizConstant;
import com.yuxianglw.entity.SysLog;
import com.yuxianglw.service.SysLogService;
import com.yuxianglw.utlis.HttpContextUtils;
import com.yuxianglw.utlis.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
/**
 * @author zhangtao
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;

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
    public void doBefore(JoinPoint joinPoint){

        try {
            //保存日志
            SysLog sysLog = new SysLog();

            //日志类型
            sysLog.setType("user_requst");
            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取请求的方法名
            String methodName = method.getName();
            //如果是退出记录日志
            if(StringUtils.equals("logout",methodName)){
                sysLog.setMethod(className + "." + methodName);
                //请求的参数
                Object[] args = joinPoint.getArgs();
                //将参数所在的数组转换成json
                String params = JSON.toJSONString(args);
                sysLog.setParam(params);
                //获取用户ip地址
                HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
                sysLog.setIp(IpUtils.getIpAddr(request));
                //调用service保存SysLog实体类到数据库
                sysLogService.save(sysLog);
            }
        } catch (Exception e) {
            log.error("记录日志失败 { }",e);
        }
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
    @AfterReturning(pointcut = "BrokerPointcut()",returning = "result")
    public void doAfterReturning(JoinPoint joinPoint,Object result){
        try {
            //保存日志
            SysLog sysLog = new SysLog();
            //日志类型
            sysLog.setType("user_requst");
            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取请求的方法名
            String methodName = method.getName();
            //如果是退出不记录日志
            sysLog.setMethod(className + "." + methodName);
            //请求的参数
            Object[] args = joinPoint.getArgs();
            //将参数所在的数组转换成json
            String params = JSON.toJSONString(args);
            sysLog.setParam(params);
            //返回值
            String results = JSON.toJSONString(result);
            sysLog.setResult(results);
            //获取用户ip地址
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            sysLog.setIp(IpUtils.getIpAddr(request));
            //调用service保存SysLog实体类到数据库
            sysLogService.save(sysLog);
        } catch (Exception e) {
           log.info("保存日志错误!");
        }
    }

    /**
     * @description  在连接点执行之后执行的通知（异常通知）
     */
    @AfterThrowing(pointcut = "BrokerPointcut()")
    public void doAfterThrowing(JoinPoint joinPoint){
        System.out.println("在连接点执行之后执行的通知（异常通知）");
    }
}
