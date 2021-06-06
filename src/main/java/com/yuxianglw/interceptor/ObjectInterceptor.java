package com.yuxianglw.interceptor;

import com.yuxianglw.common.CommonConstant;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @author zhangtao
 * @CreateDateTime: 2020/1/14 20:32
 * @ModifyDateTime:
 * @Description: 用于赋值信息的拦截器
 * @Since: 1.0
 **/
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class ObjectInterceptor implements Interceptor {

        @Override
        public Object intercept(final Invocation invocation) throws Exception {
            if (invocation.getTarget() instanceof Executor && invocation.getArgs().length == 2) {
                final Executor executor = (Executor) invocation.getTarget();
                // 获取第一个参数
                final MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
                final Object paramObj = invocation.getArgs()[1];
                if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
                    return this.executeInsert(executor, ms, paramObj);
                } else if (ms.getSqlCommandType() == SqlCommandType.UPDATE) {
                    return this.executeUpdate(executor, ms, paramObj);
                } else if (ms.getSqlCommandType() == SqlCommandType.DELETE) {
                    return this.executeDelete(executor, ms, paramObj);
                }
            }
            return invocation.proceed();
        }

        @Override
        public Object plugin(final Object target) {
            return Plugin.wrap(target, this);
        }

        @Override
        public void setProperties(final Properties properties) {

        }

        /**
         * 新增操作
         *
         * @param executor executor
         * @param ms       ms
         * @param paramObj 参数
         * @return 返回执行结果
         */
        private Object executeInsert(final Executor executor, final MappedStatement ms, final Object paramObj) throws Exception {
            //获取当前登录人
            final String username = (String) SecurityUtils.getSubject().getPrincipal();
            final Field[] fields = paramObj.getClass().getDeclaredFields();
            for (final Field field : fields) {
                field.setAccessible(true);
                final String fieldName = field.getName();
                switch (fieldName) {
                    case "createdBy":
                        field.set(paramObj, username);
                        break;
                    case "createdTime":
                        field.set(paramObj, LocalDateTime.now());
                        break;
                    case "updatedBy":
                        field.set(paramObj, username);
                        break;
                    case "updatedTime":
                        field.set(paramObj, LocalDateTime.now());
                        break;
                    default:
                        break;
                }
            }
            return executor.update(ms, paramObj);
        }

        /**
         * 修改操作
         *
         * @param executor executor
         * @param ms       ms
         * @param paramObj 参数
         * @return 返回执行结果
         */
        private Object executeUpdate(final Executor executor, final MappedStatement ms, final Object paramObj) throws Exception {
            if (paramObj instanceof MapperMethod.ParamMap) {
                //获取当前登录人
                final String username = (String) SecurityUtils.getSubject().getPrincipal();
                final MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) paramObj;
                for (final Object entity : paramMap.values()) {
                    final Field[] fields = entity.getClass().getDeclaredFields();
                    for (final Field field : fields) {
                        field.setAccessible(true);
                        final String fieldName = field.getName();
                        switch (fieldName) {
                            case "updatedBy":
                                field.set(entity, username);
                                break;
                            case "updatedTime":
                                field.set(entity, LocalDateTime.now());
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            return executor.update(ms, paramObj);
        }

        /**
         * 删除操作
         *
         * @param executor executor
         * @param ms       ms
         * @param paramObj 参数
         * @return 返回执行结果
         */
        private Object executeDelete(final Executor executor, final MappedStatement ms, final Object paramObj) throws Exception {
            final Field[] fields = paramObj.getClass().getDeclaredFields();
            for (final Field field : fields) {
                field.setAccessible(true);
                final String fieldName = field.getName();
                switch (fieldName) {
                    case "delFlag":
                        field.set(paramObj, CommonConstant.DEL_FLAG_1);
                        break;
                    default:
                        break;
                }
            }
            return executor.update(ms, paramObj);
        }

    }
